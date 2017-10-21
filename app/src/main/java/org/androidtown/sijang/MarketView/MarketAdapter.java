package org.androidtown.sijang.MarketView;

/**
 * Created by 안탄 on 2017-10-15.
 */


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Market_Data;
import org.androidtown.sijang.R;

import java.util.List;


/**
 * Created by JINWOO on 2017-03-13.
 */

public class MarketAdapter extends BaseAdapter {
    private final static String TAG = "PINGPONG";
    List<Market_Data> datas;
    Context context;
    LayoutInflater inflater;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;


    public MarketAdapter(List<Market_Data> datas, Context context){
        Log.i(TAG, "=================================ListAdapter()");
        this.datas = datas;
        this.context = context;
        this.inflater = (LayoutInflater) context. getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        Log.i(TAG, "=================================getCount()");
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i(TAG, "=================================getItem()");
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "=================================getItemId()");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "=================================getView()");
        if(convertView == null){
            convertView = inflater.inflate(R.layout.marketlist_item, null);
        }
        TextView market_name = (TextView) convertView.findViewById(R.id.marketlist_item_text_name);
        //ImageView market_img = (ImageView) convertView.findViewById(R.id.marketlist_item_image_market);

        Market_Data market_data = datas.get(position);
        market_name.setText(market_data.get시장이름());

        firebaseStorage = FirebaseStorage.getInstance();
        rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");

        /*StorageReference islandRef1 = rootReference.child(market_data.get사진경로()+"/1");
        Glide.with(context.getApplicationContext()).using(new FirebaseImageLoader())
                .load(islandRef1).into(market_img);*/



        return convertView;
    }
}
