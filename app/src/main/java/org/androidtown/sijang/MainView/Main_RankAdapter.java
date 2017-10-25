package org.androidtown.sijang.MainView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.MarketRank_Data;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyuk on 2017-07-03.
 */

public class Main_RankAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<MarketRank_Data> list = new ArrayList<>();
    private String place;
    LayoutInflater inflater;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;


    public Main_RankAdapter(List<MarketRank_Data> list, Context mContext) {
        super();
        this.mContext = mContext;
        this.list = list;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_main_rank_item, null);
        }
        TextView market_name = (TextView) convertView.findViewById(R.id.fragment_main_rank_item_market);
        ImageView rank = (ImageView) convertView.findViewById(R.id.fragment_main_rank_item_rank);
        ImageView market_img = (ImageView) convertView.findViewById(R.id.fragment_main_rank_item_img);

        MarketRank_Data marketRank_data = list.get(position);
        market_name.setText(marketRank_data.get시장이름());

        firebaseStorage = FirebaseStorage.getInstance();
        rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");

        System.out.println("==========================="+position);

        StorageReference marketImageRef = rootReference.child(marketRank_data.get시장이름()+"/0.jpg");
        Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader()).load(marketImageRef).into(market_img);

        switch (position){
            case 0 : rank.setImageResource(R.drawable.goldmedal); break;
            case 1 : rank.setImageResource(R.drawable.goldmedal); break;
            case 2: rank.setImageResource(R.drawable.goldmedal); break;
        }

        return convertView;

    }

}