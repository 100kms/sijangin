package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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


public class MarketMainList extends MainActivity {
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference reviewRef;
    private StorageReference rootReference;
    private ListView mainreview = null;
    private MainReviewList_Adapter mainreview_adapter = null;


    List<Review_Data> review_datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketmainlist);

        Intent gIntent = getIntent();
        String place = gIntent.getStringExtra("place");
        String market_name = gIntent.getStringExtra("market_name");
        String latitude = gIntent.getStringExtra("위도");
        String longitude = gIntent.getStringExtra("경도");
        String address = gIntent.getStringExtra("주소");
        String content = gIntent.getStringExtra("내용");
        System.out.println(latitude+"====================================================");
        TextView marketName = (TextView) findViewById(R.id.marketmainlist_text_name);

        marketName.setText(market_name);

    }
}
      /*  database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        reviewRef = database.getReference("리뷰").child("시장별").child(market_name);
        reviewRef.addValueEventListener(reviewListener);

        mainreview = (ListView) findViewById(R.id.marketmainlist_listview_review);
       // mainreview_adapter = new Market(review_datas, this);

        mainreview.setAdapter(mainreview_adapter);




        Button write = (Button)findViewById(R.id.marketmainlist_btn_review);

        write.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketMainList.this, Review_Write.class);
                startActivity(intent);
            }
        });


    }
    ValueEventListener reviewListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            review_datas.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Review_Data review_data = snapshot.getValue(Review_Data.class);
                review_datas.add(review_data);
            }
            Collections.reverse(review_datas);
            mainreview_adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public static void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }

        int totalHeight = 0;
        for(int i = 0; i<listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}*/