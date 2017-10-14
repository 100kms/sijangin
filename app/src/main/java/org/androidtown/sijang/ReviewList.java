package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hyuk on 2017-07-02.
 */

public class ReviewList extends MainActivity {
    private ListView reviewlist = null;
    private ReviewList_Adapter reviewList_adapter = null;

    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;
    DatabaseReference review_marketRef;
    DatabaseReference review_userRef;
    DatabaseReference review_allRef;

    List<Review_Data> datas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reviewlist);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        review_allRef = database.getReference("리뷰").child("전체");

        review_allRef.addValueEventListener(reviewListener);

        reviewlist = (ListView)findViewById(R.id.reviewList_listview);
        reviewList_adapter = new ReviewList_Adapter(datas, this);
        reviewlist.setAdapter(reviewList_adapter);

        int[] img = {};
        int[] img1 = {R.drawable.img1, R.drawable.img2};
        int[] img2 = {R.drawable.hyuk1};
        int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};


//        reviewList_adapter.additem("자양시장", "해남치킨", "keealsgur", "2017-07-24", "너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!", 3.5f, img2);
//        reviewList_adapter.additem("광장시장", "맛나파전", "alstnqkqh", "2017-07-24", "민수바보", 3.5f, img1);
        /*for(Review_Data rd : datas){
            Toast.makeText(getApplicationContext(), rd.getContent(), Toast.LENGTH_SHORT).show();
            reviewList_adapter.additem(rd.market_key,rd.all_key,rd.name,"등록일","민수바보", 3.5f, img1);
        }//getㄴter setter 불러오는거*/

        reviewlist.setAdapter(reviewList_adapter);
    }

    ValueEventListener reviewListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            datas.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                Review_Data review_data = snapshot.getValue(Review_Data.class);
                datas.add(review_data);
            }
            Collections.reverse(datas);
            reviewList_adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

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
