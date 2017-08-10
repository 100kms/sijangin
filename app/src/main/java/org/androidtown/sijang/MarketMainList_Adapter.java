package org.androidtown.sijang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LG on 2017-07-06.
 */

public class MarketMainList_Adapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<Data> mMarketData = new ArrayList <Data>();
    //알람 아이템들의 정보값들을 배열로 저장 리스트 포지션 하나당 들어갈 것들

    public MarketMainList_Adapter(Context mContext){
        super();
        this.mContext = mContext;
    }//생성자

    @Override
    public int getCount() {
        return mMarketData.size();
    } //리스트뷰 포지션의 개수가 배열의 수만큼 나오게한다

    @Override
    public Object getItem(int position) {
        return mMarketData.get(position);
    } //각 리스트 포지션마다 배열의 저장된 데이터 반환

    @Override
    public long getItemId(int position) {
        return position;
    } //해당 포지션 반환

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.marketmainlist_item, null);

            holder.food_name = (TextView)convertView.findViewById(R.id.marketmainlist_item_text_name);
            holder.way_btn = (Button)convertView.findViewById(R.id.marketmainlist_item_btn_way);
            holder.food_image = (ImageView)convertView.findViewById(R.id.marketmainlist_item_image_food);

            //홀더에 저장되있는 것들을 이제 view에 뿌려준다

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Data mData = mMarketData.get(position);

        holder.food_name.setText(mData.minfo);
        holder.way_btn.setVisibility(View.VISIBLE);
        holder.food_image.setVisibility(View.VISIBLE);

        return convertView;
    }

    public void addItem(String minfo){
        Data addInfo = null;
        addInfo = new Data();
        addInfo.minfo = minfo;

        mMarketData.add(addInfo);
    }

    private class ViewHolder {
        public TextView food_name;
        public Button way_btn;
        public ImageView food_image;
    } //알람 리스트뷰의 아이템 홀더

    public class Data {

        public String minfo;
    }
}
