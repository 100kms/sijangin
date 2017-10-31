package org.androidtown.sijang.MarketView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

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

public class MarketReviewFragment extends AppCompatActivity {
    private static MarketReviewFragment instance = null;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference bbsRef;
    private Review review;
    private MarketReviewRecyclerAdapter marketReviewRecyclerAdapter;
    private int review_index = 0;
    private int review_read_index = 0;
    private boolean isMoreLoading = true;
    private String marketname;
    public static MarketReviewFragment getInstance(){
        if(instance == null){
            instance = new MarketReviewFragment();
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketreviewlist);
        recyclerView = (RecyclerView) findViewById(R.id.marketreviewListRecyclerView);

        Intent intent = getIntent();
        marketname = intent.getStringExtra("marketname");

        Toolbar toolbar = (Toolbar)findViewById(R.id.marketreview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(marketname + " 리뷰");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_back);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        marketReviewRecyclerAdapter = new MarketReviewRecyclerAdapter(this.getApplicationContext());


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
                    Log.i("kkkkkk", review_read_index + "!!start");
                    getData();
                }

            }
        });
        recyclerView.setAdapter(marketReviewRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        marketReviewRecyclerAdapter.startProgress();
        for (int i = review_read_index; i < review_read_index+10; i++) {
            bbsRef = database.getReference("review").child("전체").child(Integer.toString(i));
            bbsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    review = dataSnapshot.getValue(Review.class);// 컨버팅되서 Bbs로........

                    if(review == null){
                        marketReviewRecyclerAdapter.endProgress();
                        marketReviewRecyclerAdapter.notifyItemRangeChanged(review_read_index, review_read_index+9);
                        review_read_index+=review_index;
                        review_index = 0;
                        return;
                    }

                    if(review.getMarketname().equals(marketname)){
                        marketReviewRecyclerAdapter.addItem(review, dataSnapshot.getKey());
                        review_index++;
                        if(review_index == review_read_index+9){
                            marketReviewRecyclerAdapter.endProgress();
                            marketReviewRecyclerAdapter.notifyItemRangeChanged(review_read_index, review_read_index+9);
                            review_index = 0;
                            review_read_index+=10;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}