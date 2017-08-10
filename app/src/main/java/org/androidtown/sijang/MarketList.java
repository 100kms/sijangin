package org.androidtown.sijang;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by hyuk on 2017-07-02.
 */

public class MarketList extends MainActivity {
   private ListView marketlist = null;
   private MarketList_Adapter marketList_adapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketlist);


        marketlist = (ListView) findViewById(R.id.marketList_listview);
        marketList_adapter = new MarketList_Adapter(this);


        marketList_adapter.additem(ContextCompat.getDrawable(this, R.drawable.market),"자양시장","해남치킨","민수족발","새마을",3.5f);
        marketList_adapter.additem(ContextCompat.getDrawable(this, R.drawable.market),"구의시장","해남치킨","민수족발","새마을",3.5f);
        marketList_adapter.additem(ContextCompat.getDrawable(this, R.drawable.market),"광장시장","해남치킨","민수족발","새마을",3.5f);


        marketlist.setAdapter(marketList_adapter);
    }
}
