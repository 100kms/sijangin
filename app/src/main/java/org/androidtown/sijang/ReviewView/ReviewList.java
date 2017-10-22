/*
package org.androidtown.sijang.ReviewView;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.androidtown.sijang.MyinfoView.ReviewRecyclerAdapter;
import org.androidtown.sijang.R;

*/
/**
 * Created by hyuk on 2017-07-02.
 *//*

public class ReviewList extends AppCompatActivity {
    private RecyclerView recyclerView = null;
    private ReviewRecyclerAdapter reviewRecyclerAdapter = null;
    private boolean isMoreLoading = false;

    Review rv;
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    DatabaseReference bbsRef;


    int[] img = {};
    int[] img1 = {R.drawable.img1, R.drawable.img2};
    int[] img2 = {R.drawable.hyuk1};
    int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reviewlist);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        bbsRef = database.getReference("review").child("전체").child("0");
        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //String key = snapshot.getKey();
                //System.out.println("asdasndl >> " + snapshot.getValue(Latitude.class).toString());
                rv = dataSnapshot.getValue(Review.class); // 컨버팅되서 Bbs로........


                reviewRecyclerAdapter.additem(rv.getMarket_text(), rv.getReplace_text(), rv.getUser_id(),
                        rv.getDate_record(), rv.getContent() ,rv.getStar(), img3, rv.getImage());


                // change메서드 안에서 onMapReady를 불러와주는 역할을 넣음
                // 여기다 넣은것은 이 데이터를 가져오는것이 생명주기를 무시하고, 액티비티 자체가 실행될때 데이터를 가져오기 때문에 데이터를 먼저가져오고 맵을 그리는 순으로 만들기 위해서이다
                // 이렇게 안하면, 데이터를 먼저 가져오라고 해도 맵을 먼저 그려버리고 그 뒤에 액티비티자체가 실행이 다되고 데이터를 가져오기 때문에

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.reviewListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerAdapter = new ReviewRecyclerAdapter(this);



        reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        reviewRecyclerAdapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);
        reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        reviewRecyclerAdapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);
        reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        reviewRecyclerAdapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);
        reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        reviewRecyclerAdapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);
        reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        reviewRecyclerAdapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);
        recyclerView.setAdapter(reviewRecyclerAdapter);
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
                Log.i("kkkkk",visibleItemCount + " " +  totalItemCount + " " + firstVisibleItem);
                if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + 1)) {
                    isMoreLoading = true;
                    reviewRecyclerAdapter.startProgress();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reviewRecyclerAdapter.endProgress();
                            reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","리뷰1",3.5f, img2);
                            reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","리뷰2",3.5f, img2);
                            reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","리뷰3",3.5f, img2);
                            reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","리뷰4",3.5f, img2);
                            reviewRecyclerAdapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","리뷰5",3.5f, img2);
                            isMoreLoading = false;
                        }
                    },2000);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reviewlist_menu, menu);
        return true;
    }


}
*/
