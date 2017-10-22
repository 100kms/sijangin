package org.androidtown.sijang.MyinfoView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.sijang.R;

/**
 * Created by CYSN on 2017-10-05.
 */

public class MyInfoFavoriteFragment extends Fragment {
    private static MyInfoFavoriteFragment instance = null;
    private RecyclerView recyclerView;
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

        MarketRecyclerAdapter marketRecyclerAdapter = new MarketRecyclerAdapter(getContext());


        //아이디에 맞는 데이터 데이터베이스에서 읽어와서 넣는방식으로 바꿔야함!

        marketRecyclerAdapter.additem(R.drawable.bun1,"자양시장");
        marketRecyclerAdapter.additem(R.drawable.bun1,"자양시장");
        marketRecyclerAdapter.additem(R.drawable.bun1,"자양시장");
        marketRecyclerAdapter.additem(R.drawable.bun1,"자양시장");
        marketRecyclerAdapter.additem(R.drawable.bun1,"자양시장");
        marketRecyclerAdapter.additem(R.drawable.bun1,"자양시장");

        recyclerView.setAdapter(marketRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}