package org.androidtown.sijang;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hyuk on 2017-07-03.
 */

public class MarketList_Adapter extends BaseAdapter{
    private Context mContext = null;
    private ArrayList<Data> list = new ArrayList<Data>();

    public MarketList_Adapter(Context mContext){
        super();
        this.mContext = mContext;
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
    public long getItemId(int position) { return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.marketlist_item, null);


            holder.market_image = (ImageView) convertView.findViewById(R.id.marketlist_item_image_market);
            holder.market_name = (TextView) convertView.findViewById(R.id.marketlist_item_text_name);
            holder.star = (RatingBar) convertView.findViewById(R.id.marketlist_item_ratingbar);
            holder.food_name1 = (TextView) convertView.findViewById(R.id.marketlist_item_text_food1);
            holder.food_name2 = (TextView) convertView.findViewById(R.id.marketlist_item_text_food2);
            holder.food_name3 = (TextView) convertView.findViewById(R.id.marketlist_item_text_food3);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }


        Data marketData =  list.get(position);

        holder.market_image.setImageDrawable(marketData.market_image);
        holder.market_name.setText(marketData.market_name);
        holder.food_name1.setText(marketData.food_name1);
        holder.food_name2.setText(marketData.food_name2);
        holder.food_name3.setText(marketData.food_name3);
        holder.star.setRating(marketData.star);




        return convertView;
    }

    public void additem(Drawable market_image, String market_name, String food_name1, String food_name2, String food_name3, float star){
        Data addinfo = new Data();
        addinfo.market_image = market_image;
        addinfo.market_name = market_name;
        addinfo.food_name1 = food_name1;
        addinfo.food_name2 = food_name2;
        addinfo.food_name3 = food_name3;
        addinfo.star = star;

        list.add(addinfo);
    }

    private class ViewHolder{
        public ImageView market_image;
        public TextView market_name;
        public TextView food_name1;
        public TextView food_name2;
        public TextView food_name3;
        public RatingBar star;
    }

    public class Data{
        public Drawable market_image;
        public String market_name;
        public String food_name1;
        public String food_name2;
        public String food_name3;
        public float star;

    }
}
