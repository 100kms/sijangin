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

import org.androidtown.sijang.R;
import org.androidtown.sijang.ReviewView.Review;

/**
 * Created by CYSN on 2017-10-05.
 */

public class MyInfoReviewFragment extends Fragment {
    private static MyInfoReviewFragment instance = null;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference bbsRef;
    private Review review;
    private ReviewRecyclerAdapter reviewRecyclerAdapter;
    private int review_index = 0;
    private int review_read_index = 0;
    private boolean isMoreLoading = true;
    private SharedPreferences pref;
    private int getAllReviewCount = 0;
    private long mtime=0;
    public static MyInfoReviewFragment getInstance() {
        if (instance == null) {
            instance = new MyInfoReviewFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo_review, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.myInfo_myReviewView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        reviewRecyclerAdapter = new ReviewRecyclerAdapter(getContext());
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
                if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + 1)) {
                    isMoreLoading = true;
                    Log.i("kkkkkk", review_read_index + "!!start");
                    getData();
                }

            }
        });
        recyclerView.setAdapter(reviewRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

    }

    @Override
    public void onDestroy() {
        review_index = 0;
        review_read_index = 0;
        isMoreLoading = true;
        super.onDestroy();
    }

    public void getData() {
        reviewRecyclerAdapter.startProgress();
        bbsRef = database.getReference("review").child("전체");//이걸로 아이디 csm인 데이터 가져 올 수 있음!!!★★★★★★
        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               String user_id =  pref.getString("user_id","");
                for(DataSnapshot item: dataSnapshot.getChildren())
                {
                    String fire_user_id=(String)item.child("user_id").getValue();
                    Log.i("kkkkk",fire_user_id + " ! " + user_id + " ! " + item.getKey());
                    if(fire_user_id.equals(user_id)){
                        Log.i("kkkkk","hello");
                        review = item.getValue(Review.class);
                        reviewRecyclerAdapter.addItem(review,item.getKey());
                    }
                }
                reviewRecyclerAdapter.endProgress();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
