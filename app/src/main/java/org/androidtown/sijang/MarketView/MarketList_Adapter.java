package org.androidtown.sijang.MarketView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Market_Data;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyuk on 2017-07-03.
 */

public class MarketList_Adapter extends BaseAdapter {
    private Context mContext = null;
    private List<Market_Data> datas = new ArrayList<>();
    private String place;
    LayoutInflater inflater;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;


    public MarketList_Adapter(List<Market_Data> datas, Context mContext, String place) {
        super();
        this.mContext = mContext;
        this.datas = datas;
        this.place = place;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.marketlist_item, null);
        }

        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.market_layout);
        TextView market_name = (TextView) convertView.findViewById(R.id.marketlist_item_text_name);
        TextView market_address = (TextView) convertView.findViewById(R.id.marketlist_item_text_address);
        ImageView market_img = (ImageView) convertView.findViewById(R.id.marketlist_item_image_market);

        final Market_Data market_data = datas.get(position);
        market_name.setText(market_data.get시장이름());
        market_address.setText(market_data.get주소());

        firebaseStorage = FirebaseStorage.getInstance();
        rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");


        StorageReference marketImageRef = rootReference.child(market_data.get시장이름()+"/0.jpg");
        Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader()).load(marketImageRef).into(market_img);

        relativeLayout.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MarketMainList.class);
                intent.putExtra("market_name",market_data.get시장이름());
                intent.putExtra("place", place);
                intent.putExtra("경도", market_data.get경도());
                intent.putExtra("위도", market_data.get위도());
                intent.putExtra("주소", market_data.get주소());
                intent.putExtra("내용", market_data.get내용());
                intent.putExtra("교통수단", market_data.get교통수단());

                //기타값추가
                mContext.startActivity(intent);
            }
        });

        return convertView;

    }

}