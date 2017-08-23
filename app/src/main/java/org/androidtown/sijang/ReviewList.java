package org.androidtown.sijang;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by hyuk on 2017-07-02.
 */

public class ReviewList extends MainActivity {
    private ListView reviewlist = null;
    private ReviewList_Adapter reviewList_adapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reviewlist);


        reviewlist = (ListView) findViewById(R.id.reviewList_listview);
        reviewList_adapter = new ReviewList_Adapter(this);

        int[] img = {};
        int[] img1 = {R.drawable.img1, R.drawable.img2};
        int[] img2 = {R.drawable.hyuk1};
        int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};


        reviewList_adapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        reviewList_adapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);


        reviewlist.setAdapter(reviewList_adapter);
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
