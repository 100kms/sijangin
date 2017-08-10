package org.androidtown.sijang;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
        int[] img2 = {R.drawable.img2};
        int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};


        reviewList_adapter.additem("자양시장","keealsgur","2017-07-24","myString",3.5f, img);
        reviewList_adapter.additem("구의시장","mh8375","2017-07-24","구져요 개구져",3.5f, img1);
        reviewList_adapter.additem("광장시장","alstnqkqh","2017-07-24","민수바보",3.5f, img2);
        reviewList_adapter.additem("자양시장","keealsgur","2017-07-24","myString",3.5f, img);
        reviewList_adapter.additem("구의시장","mh8375","2017-07-24","구져요 개구져",3.5f, img1);
        reviewList_adapter.additem("광장시장","alstnqkqh","2017-07-24","민수바보",3.5f, img2);
        reviewList_adapter.additem("자양시장","keealsgur","2017-07-24","myString",3.5f, img);
        reviewList_adapter.additem("구의시장","mh8375","2017-07-24","구져요 개구져",3.5f, img1);
        reviewList_adapter.additem("광장시장","alstnqkqh","2017-07-24","민수바보",3.5f, img2);


        reviewlist.setAdapter(reviewList_adapter);
    }

}
