package org.androidtown.sijang.MarketView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Market_Data;
import org.androidtown.sijang.MainView.MainActivity;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyuk on 2017-07-02.
 */

public class MarketList extends MainActivity {
    private ListView marketlist = null;
    private MarketList_Adapter marketList_adapter = null;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference marketRef;
    private StorageReference rootReference;

    List<Market_Data> datas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketlist);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        String place = intent.getStringExtra("place");
        place = "은서마";
        marketRef = database.getReference("시장").child(place);
        marketRef.addValueEventListener(marketListener);


        marketlist = (ListView) findViewById(R.id.marketList_listview);
        marketList_adapter = new MarketList_Adapter(datas, this, place);

        marketlist.setAdapter(marketList_adapter);
    }

    ValueEventListener marketListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            datas.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Market_Data market_data = snapshot.getValue(Market_Data.class);
                datas.add(market_data);
            }

            marketList_adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
