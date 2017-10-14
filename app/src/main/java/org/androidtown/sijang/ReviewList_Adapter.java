package org.androidtown.sijang;

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

import java.util.List;

/**
 * Created by hyuk on 2017-07-03.
 */

public class ReviewList_Adapter extends BaseAdapter{
    private Context mContext = null;
    List<Review_Data> datas;
    LayoutInflater inflater;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;

    public ReviewList_Adapter(List<Review_Data> datas, Context mContext){
        super();
        this.mContext = mContext;
        this.datas = datas;
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public long getItemId(int position) { return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.reviewlist_item, null);
        }

            TextView market_text = (TextView) convertView.findViewById(R.id.reviewlist_item_text_market);
            TextView replace_text = (TextView) convertView.findViewById(R.id.reviewlist_item_text_replace);
            TextView userid = (TextView) convertView.findViewById(R.id.reviewlist_item_text_userid);
            TextView created = (TextView) convertView.findViewById(R.id.reviewlist_item_text_created);
            TextView review = (TextView) convertView.findViewById(R.id.reviewlist_item_text_review);
            ImageView img_1 = (ImageView) convertView.findViewById(R.id.reviewlist_item_img_review1);
            ImageView img_2 = (ImageView) convertView.findViewById(R.id.reviewlist_item_img_review2);
            ImageView img_3 = (ImageView) convertView.findViewById(R.id.reviewlist_item_img_review3);



            Review_Data review_data = datas.get(position);
            review.setText(review_data.getContent());
            replace_text.setText(review_data.getTitle());

        img_1.setVisibility(View.GONE);
        img_2.setVisibility(View.GONE);
        img_3.setVisibility(View.GONE);

        firebaseStorage = FirebaseStorage.getInstance();
        rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
        System.out.println("========================================================"+review_data.getCount());
            if(review_data.getCount().equals("1")){
                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.GONE);
                img_3.setVisibility(View.GONE);
                StorageReference islandRef1 = rootReference.child(review_data.getAll_key()+"/1");
                    Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef1).into(img_1);
            }else if(review_data.getCount().equals("2")){
                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.VISIBLE);
                img_3.setVisibility(View.GONE);
                StorageReference islandRef1 = rootReference.child(review_data.getAll_key()+"/1");
                Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef1).into(img_1);

                StorageReference islandRef2 = rootReference.child(review_data.getAll_key()+"/2");
                Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef2).into(img_2);
            }else if(review_data.getCount().equals("3")){
                img_1.setVisibility(View.VISIBLE);
                img_2.setVisibility(View.VISIBLE);
                img_3.setVisibility(View.VISIBLE);
                StorageReference islandRef1 = rootReference.child(review_data.getAll_key()+"/1");
                Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef1).into(img_1);

                StorageReference islandRef2 = rootReference.child(review_data.getAll_key()+"/2");
                Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef2).into(img_2);

                StorageReference islandRef3 = rootReference.child(review_data.getAll_key()+"/3");
                Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef3).into(img_3);
            }








            return convertView;
//            RatingBar star = (RatingBar) convertView.findViewById(R.id.reviewlist_item_ratingbar);
//            ImageView img_3 = (ImageView) convertView.findViewById(R.id.reviewlist_item_img_review3);

//            convertView.setTag(holder);
//        } else{
//            holder = (ViewHolder)convertView.getTag();
//        }
//
//        ReviewList_Adapter.Data reviewData =  list.get(position);
//
//
//        holder.market_text.setText(reviewData.market_text);
//        holder.market_text.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent =  new Intent(mContext, MarketMainList.class);
//                mContext.startActivity(intent);
//            }
//        });
//        holder.replace_text.setText(reviewData.replace_text);
//        holder.userid.setText(reviewData.userid);
//        holder.created.setText(reviewData.created);
//        holder.review.setText(reviewData.review);
//        holder.star.setRating(reviewData.star);
//
//        int count=reviewData.img.length;
//
//        switch(count){
//            case 0 :
//                holder.img_1.setVisibility(View.GONE);
//                holder.img_2.setVisibility(View.GONE);
//                holder.img_3.setVisibility(View.GONE);
//                break;
//            case 1 :
//                holder.img_1.setImageResource(reviewData.img[0]);
//                holder.img_1.setVisibility(View.VISIBLE);
//                holder.img_2.setVisibility(View.GONE);
//                holder.img_3.setVisibility(View.GONE);
//                break;
//            case 2 :
//                holder.img_1.setImageResource(reviewData.img[0]);
//                holder.img_1.setVisibility(View.VISIBLE);
//                holder.img_2.setImageResource(reviewData.img[1]);
//                holder.img_2.setVisibility(View.VISIBLE);
//                holder.img_3.setVisibility(View.GONE);
//                break;
//            case 3 :
//                holder.img_1.setImageResource(reviewData.img[0]);
//                holder.img_1.setVisibility(View.VISIBLE);
//                holder.img_2.setImageResource(reviewData.img[1]);
//                holder.img_2.setVisibility(View.VISIBLE);
//                holder.img_3.setImageResource(reviewData.img[2]);
//                holder.img_3.setVisibility(View.VISIBLE);
//                break;
//        }
//        count = 0;
//
//        return convertView;
    }
}
