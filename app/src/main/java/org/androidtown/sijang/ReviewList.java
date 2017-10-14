package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by hyuk on 2017-07-02.
 */
public class ReviewList extends MainActivity {
    private RecyclerView recyclerView = null;
    private ReviewRecyclerAdapter reviewRecyclerAdapter = null;
    private boolean isMoreLoading = false;

    int[] img = {};
    int[] img1 = {R.drawable.img1, R.drawable.img2};
    int[] img2 = {R.drawable.hyuk1};
    int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reviewlist);


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.reviewlist_menu_sort:
                Intent intent = new Intent(getApplicationContext(), ReviewListSort.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
