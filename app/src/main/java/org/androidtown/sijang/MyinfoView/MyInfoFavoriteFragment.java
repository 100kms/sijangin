package org.androidtown.sijang.MyinfoView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.androidtown.sijang.Data.Favorite;
import org.androidtown.sijang.R;

/**
 * Created by CYSN on 2017-10-05.
 */

public class MyInfoFavoriteFragment extends Fragment {
    private static MyInfoFavoriteFragment instance = null;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference bbsRef;
    private Favorite favorite;
    private MarketRecyclerAdapter marketRecyclerAdapter;
    private int favorite_index = 0;
    private int favorite_read_index = 0;
    private boolean isMoreLoading = true;
    private SharedPreferences pref;
    public static MyInfoFavoriteFragment getInstance(){
        if(instance == null){
            instance = new MyInfoFavoriteFragment();
        }
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo_favorite, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myInfo_myFavoriteView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        marketRecyclerAdapter = new MarketRecyclerAdapter(getContext());

        pref = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);


        //아이디에 맞는 데이터 데이터베이스에서 읽어와서 넣는방식으로 바꿔야함!

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //Log.i("kkkkk",visibleItemCount + " " +  totalItemCount + " " + firstVisibleItem);
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + 1)) {
                    isMoreLoading = true;
                    Log.i("kkkkkk", favorite_read_index + "!!start");
                    getData();
                }

            }
        });
        recyclerView.setAdapter(marketRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

    }

    @Override
    public void onDestroy() {
        favorite_index = 0;
        favorite_read_index = 0;
        isMoreLoading = true;
        super.onDestroy();
    }

    public void getData() {
        marketRecyclerAdapter.startProgress();
        for (int i = favorite_read_index; i < favorite_read_index+10; i++) {
            bbsRef = database.getReference("즐겨찾기").child(Integer.toString(i));
            bbsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    favorite = dataSnapshot.getValue(Favorite.class); // 컨버팅되서 Bbs로........

                    if(favorite == null){
                        marketRecyclerAdapter.endProgress();
                        marketRecyclerAdapter.notifyItemRangeChanged(favorite_read_index, favorite_read_index+9);
                        favorite_read_index+=favorite_index;
                        favorite_index = 0;
                        return;
                    }

                    if(favorite.getUser_id().equals(pref.getString("user_id", "")) && favorite.getState().equals("on")){
                        marketRecyclerAdapter.addItem(favorite, favorite.getMarketName());
                        favorite_index++;
                        if(favorite_index == favorite_read_index+9){
                            marketRecyclerAdapter.endProgress();
                            marketRecyclerAdapter.notifyItemRangeChanged(favorite_read_index, favorite_read_index+9);
                            favorite_index = 0;
                            favorite_read_index+=10;
                            isMoreLoading = false;
                        }
                    }


                    // change메서드 안에서 onMapReady를 불러와주는 역할을 넣음
                    // 여기다 넣은것은 이 데이터를 가져오는것이 생명주기를 무시하고, 액티비티 자체가 실행될때 데이터를 가져오기 때문에 데이터를 먼저가져오고 맵을 그리는 순으로 만들기 위해서이다
                    // 이렇게 안하면, 데이터를 먼저 가져오라고 해도 맵을 먼저 그려버리고 그 뒤에 액티비티자체가 실행이 다되고 데이터를 가져오기 때문에
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
    }
}