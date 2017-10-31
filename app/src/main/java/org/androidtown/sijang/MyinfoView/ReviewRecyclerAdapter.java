package org.androidtown.sijang.MyinfoView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.R;
import org.androidtown.sijang.ReviewView.Review;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext = null;
    private ArrayList<ReviewData> arrayList;
    private final int NORMAL_TYPE = 1;
    private final int PROGRESS_TYPE = 2;
    private int progressPos = -1;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
    private FirebaseImageLoader firebaseImageLoader = new FirebaseImageLoader();
    private String image_index="0/";
    public ReviewRecyclerAdapter(Context context){
        this.mContext = context;
        arrayList = new ArrayList<ReviewData>();
    }
    public synchronized void addItem(Review review, String index){
        image_index = index + "/";
        arrayList.add(new ReviewData(review,image_index));

        notifyItemInserted(getItemCount()-1);
    }
    public class ProgressViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.reviewProgressBar);
        }
    }
    public class DataViewHolder extends RecyclerView.ViewHolder{
        public TextView market_text;
        public TextView user_id;
        public TextView data_record;
        public TextView review;
        public TextView title;
        public RatingBar star;
        public ImageView img_1;
        public ImageView img_2;
        public ImageView img_3;
        public Context context;
        public ReviewRecyclerAdapter myRecyclerAdapter;

        public DataViewHolder(Context context, View itemView, ReviewRecyclerAdapter myRecyclerAdapter) {
            super(itemView);
            this.market_text = (TextView) itemView.findViewById(R.id.reviewlist_item_text_market);
            this.title = (TextView) itemView.findViewById(R.id.reviewlist_item_text_title);
            this.user_id = (TextView) itemView.findViewById(R.id.reviewlist_item_text_userid);
            this.data_record = (TextView) itemView.findViewById(R.id.reviewlist_item_text_record);
            this.review = (TextView) itemView.findViewById(R.id.reviewlist_item_text_review);
            this.star = (RatingBar) itemView.findViewById(R.id.reviewlist_item_ratingbar);
            this.img_1 = (ImageView) itemView.findViewById(R.id.reviewlist_item_img_review1);
            this.img_2 = (ImageView) itemView.findViewById(R.id.reviewlist_item_img_review2);
            this.img_3 = (ImageView) itemView.findViewById(R.id.reviewlist_item_img_review3);
            this.context = context;
            this.myRecyclerAdapter = myRecyclerAdapter;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof DataViewHolder) {
            DataViewHolder holder =  ((DataViewHolder)viewHolder);
            ReviewData reviewData = arrayList.get(position);
            holder.market_text.setText(reviewData.getReview().getMarketname());
            holder.title.setText(reviewData.getReview().getTitle());
            holder.user_id.setText(reviewData.getReview().getName());
            holder.data_record.setText(reviewData.getReview().getDate());
            holder.review.setText(reviewData.getReview().getContent());
            holder.star.setRating((float)reviewData.getReview().getStar());
            int count = reviewData.getReview().getImg_count();
            StorageReference islandRef;
            StorageReference islandRef2;
            StorageReference islandRef3;
            switch (count) {
                case 0:
                    holder.img_1.setVisibility(View.GONE);
                    holder.img_2.setVisibility(View.GONE);
                    holder.img_3.setVisibility(View.GONE);
                    break;
                case 1:
                    islandRef = rootReference.child(reviewData.image_URL+"1");
                    setImage(islandRef,holder.img_1);
                    holder.img_1.setVisibility(View.VISIBLE);
                    holder.img_2.setVisibility(View.GONE);
                    holder.img_3.setVisibility(View.GONE);
                    break;
                case 2:
                    islandRef = rootReference.child(reviewData.image_URL+"1");
                    islandRef2 = rootReference.child(reviewData.image_URL+"2");
                    setImage(islandRef,holder.img_1);
                    setImage(islandRef2,holder.img_2);
                    holder.img_1.setVisibility(View.VISIBLE);
                    holder.img_2.setVisibility(View.VISIBLE);
                    holder.img_3.setVisibility(View.GONE);
                    break;
                case 3:
                    islandRef = rootReference.child(reviewData.image_URL+"1");
                    islandRef2 = rootReference.child(reviewData.image_URL+"2");
                    islandRef3 = rootReference.child(reviewData.image_URL+"3");
                    setImage(islandRef,holder.img_1);
                    setImage(islandRef2,holder.img_2);
                    setImage(islandRef3,holder.img_3);
                    holder.img_1.setVisibility(View.VISIBLE);
                    holder.img_2.setVisibility(View.VISIBLE);
                    holder.img_3.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
    public void setImage(StorageReference storageReference, ImageView imageView){
        if(storageReference != null && imageView != null) {
            Glide.with(mContext.getApplicationContext()).using(firebaseImageLoader)
                    .load(storageReference).thumbnail(0.1f).override(200, 300)
                    .into(imageView);
        }
    }
    @Override
    public int getItemViewType(int position) {  // position에 해당하는 viewtype을 리턴
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous

        return arrayList.get(position) != null ? NORMAL_TYPE : PROGRESS_TYPE;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void removeItem(int p) {
        arrayList.remove(p);
        notifyItemRemoved(p);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = null;
        switch (viewType) {
            case NORMAL_TYPE:{
                view = inflater.inflate(R.layout.reviewlist_item, parent, false);
                DataViewHolder viewHolder = new DataViewHolder(context, view, this);
                return viewHolder;
            }
            case PROGRESS_TYPE:{
                view = inflater.inflate(R.layout.progress_layout, parent, false);
                ProgressViewHolder progressViewHolder = new ProgressViewHolder(view);
                return progressViewHolder;
            }   //2번 뷰타입
        }

        return null;
    }

    public void startProgress(){
        if(progressPos == -1) {
            arrayList.add(null);
            progressPos = arrayList.size() - 1;
            notifyItemInserted(progressPos);
        }
    }
    public void endProgress(){
        if(progressPos != -1) {
            arrayList.remove(progressPos);
            notifyItemRemoved(progressPos);
            notifyItemRangeChanged(0,arrayList.size());
            progressPos = -1;
        }
    }
    class ReviewData{
        private Review review;
        private String image_URL;

        public ReviewData(Review review, String image_URL) {
            this.review = review;
            this.image_URL = image_URL;
        }

        public Review getReview() {
            return review;
        }
    }
}