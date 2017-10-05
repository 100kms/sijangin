package org.androidtown.sijang;

import android.content.Context;
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

public class MainReviewList_Adapter extends BaseAdapter{
    private Context mContext = null;
    private ArrayList<Data> list = new ArrayList<Data>();

    public MainReviewList_Adapter(Context mContext){
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
        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mainreviewlist_item, null);


            holder.market_text = (TextView) convertView.findViewById(R.id.mainreviewlist_item_text_market);
            holder.replace_text = (TextView) convertView.findViewById(R.id.mainreviewlist_item_text_replace);
            holder.userid = (TextView) convertView.findViewById(R.id.mainreviewlist_item_text_userid);
            holder.created = (TextView) convertView.findViewById(R.id.mainreviewlist_item_text_created);
            holder.review = (TextView) convertView.findViewById(R.id.mainreviewlist_item_text_review);
            holder.star = (RatingBar) convertView.findViewById(R.id.mainreviewlist_item_ratingbar);
            holder.img_1 = (ImageView) convertView.findViewById(R.id.mainreviewlist_item_img_review1);
            holder.img_2 = (ImageView) convertView.findViewById(R.id.mainreviewlist_item_img_review2);
            holder.img_3 = (ImageView) convertView.findViewById(R.id.mainreviewlist_item_img_review3);

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        MainReviewList_Adapter.Data reviewData =  list.get(position);


        holder.market_text.setText(reviewData.market_text);
        holder.replace_text.setText(reviewData.replace_text);
        holder.userid.setText(reviewData.userid);
        holder.created.setText(reviewData.created);
        holder.review.setText(reviewData.review);
        holder.star.setRating(reviewData.star);

        int count=reviewData.img.length;

        switch(count){
            case 0 :
                holder.img_1.setVisibility(View.GONE);
                holder.img_2.setVisibility(View.GONE);
                holder.img_3.setVisibility(View.GONE);
                break;
            case 1 :
                holder.img_1.setImageResource(reviewData.img[0]);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setVisibility(View.GONE);
                holder.img_3.setVisibility(View.GONE);
                break;
            case 2 :
                holder.img_1.setImageResource(reviewData.img[0]);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setImageResource(reviewData.img[1]);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setVisibility(View.GONE);
                break;
            case 3 :
                holder.img_1.setImageResource(reviewData.img[0]);
                holder.img_1.setVisibility(View.VISIBLE);
                holder.img_2.setImageResource(reviewData.img[1]);
                holder.img_2.setVisibility(View.VISIBLE);
                holder.img_3.setImageResource(reviewData.img[2]);
                holder.img_3.setVisibility(View.VISIBLE);
                break;
        }
        count = 0;

        return convertView;
    }

    public void additem(String market_text ,String replace_text, String userid, String created, String review, float star, int[] img){
        Data addinfo = new Data();
        addinfo.market_text = market_text+" > ";
        addinfo.replace_text = replace_text;
        addinfo.userid = userid;
        addinfo.created = created;
        addinfo.review = review;
        addinfo.star = star;
        addinfo.img = img;

        list.add(addinfo);
    }

    private class ViewHolder{
        public TextView market_text;
        public TextView replace_text;
        public TextView userid;
        public TextView created;
        public TextView review;
        public RatingBar star;
        public ImageView img_1;
        public ImageView img_2;
        public ImageView img_3;
    }

    public class Data{
        public String market_text;
        public String replace_text;
        public String userid;
        public String created;
        public String review;
        public float star;
        public int[] img;
    }
}