package org.androidtown.sijang.MarketView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.androidtown.sijang.Data.Market_Data;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyuk on 2017-07-02.
 */

public class MarketList extends AppCompatActivity {
    private ListView marketlist = null;
    private MarketList_Adapter marketList_adapter = null;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference marketRef;
    private StorageReference rootReference;
    private Intent intent;
    List<Market_Data> datas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketlist);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        String place = intent.getStringExtra("place");
        marketRef = database.getReference("시장").child(place);
        marketRef.addValueEventListener(marketListener);

        Toolbar toolbar = (Toolbar)findViewById(R.id.market_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("지역별 시장");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_back);

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
    protected  void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
