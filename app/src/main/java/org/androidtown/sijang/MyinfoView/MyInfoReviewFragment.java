package org.androidtown.sijang.MyinfoView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.labo.kaji.fragmentanimations.MoveAnimation;

import org.androidtown.sijang.R;

/**
 * Created by CYSN on 2017-10-05.
 */

public class MyInfoReviewFragment extends Fragment {
    private static MyInfoReviewFragment instance = null;
    private RecyclerView recyclerView;
    public static MyInfoReviewFragment getInstance(){
        if(instance == null){
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


        ReviewRecyclerAdapter reviewRecyclerAdapter = new ReviewRecyclerAdapter(getContext());
        int[] img = {};
        int[] img1 = {R.drawable.img1, R.drawable.img2};
        int[] img2 = {R.drawable.hyuk1};
        int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
        //아이디에 맞는 데이터 데이터베이스에서 읽어와서 넣는방식으로 바꿔야함!
        reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","이건 리뷰에요!",3.5f, img2);
        reviewRecyclerAdapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);
        recyclerView.setAdapter(reviewRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
