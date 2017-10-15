package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hyuk on 2017-07-02.
 */

public class MarketList extends MainActivity {
   private ListView marketlist = null;
   private MarketAdapter marketList_adapter;

    FirebaseDatabase database;
    DatabaseReference marketRef;

    List<Market_Data> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketlist);

        database = FirebaseDatabase.getInstance();

        Intent intent= getIntent();
        String s=intent.getStringExtra("place");

        System.out.println("플레이스 : " + s);

        marketRef = database.getReference("시장").child(s);


        marketRef.addValueEventListener(marketListListener);

        marketlist = (ListView) findViewById(R.id.marketList_listview);
        marketList_adapter = new MarketAdapter(datas, this);




        marketlist.setAdapter(marketList_adapter);
    }

    ValueEventListener marketListListener= new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            datas.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                Market_Data market_data = snapshot.getValue(Market_Data.class);
                datas.add(market_data);

            }
            Collections.reverse(datas);
            marketList_adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
