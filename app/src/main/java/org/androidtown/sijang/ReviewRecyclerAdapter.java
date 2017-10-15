package org.androidtown.sijang;

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

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext = null;
    private ArrayList<Data> arrayList;
    private final int NORMAL_TYPE = 1;
    private final int PROGRESS_TYPE = 2;
    private int progressPos = -1;
    public ReviewRecyclerAdapter(Context context){
        this.mContext = context;
        arrayList = new ArrayList<Data>();
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

        arrayList.add(addinfo);
        notifyItemInserted(arrayList.size()-1);
    }
    public void additem(String market_text ,String replace_text, String userid, String created, String review, float star,int[] img, String img_url){
        Data addinfo = new Data();
        addinfo.market_text = market_text+" > ";
        addinfo.replace_text = replace_text;
        addinfo.userid = userid;
        addinfo.created = created;
        addinfo.review = review;
        addinfo.star = star;
        addinfo.img = img;
        addinfo.img_url = img_url;

        arrayList.add(addinfo);
        notifyItemInserted(arrayList.size()-1);
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
        public TextView replace_text;
        public TextView userid;
        public TextView created;
        public TextView review;
        public RatingBar star;
        public ImageView img_1;
        public ImageView img_2;
        public ImageView img_3;
        public Context context;
        public ReviewRecyclerAdapter myRecyclerAdapter;

        public DataViewHolder(Context context, View itemView, ReviewRecyclerAdapter myRecyclerAdapter) {
            super(itemView);
            this.market_text = (TextView) itemView.findViewById(R.id.reviewlist_item_text_market);
            this.replace_text = (TextView) itemView.findViewById(R.id.reviewlist_item_text_replace);
            this.userid = (TextView) itemView.findViewById(R.id.reviewlist_item_text_userid);
            this.created = (TextView) itemView.findViewById(R.id.reviewlist_item_text_created);
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
            Data reviewData = arrayList.get(position);
            holder.market_text.setText(reviewData.market_text);
 /*       holder.market_text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(mContext, MarketMainList.class);
                mContext.startActivity(intent);
            }
        });*/
            holder.replace_text.setText(reviewData.replace_text);
            holder.userid.setText(reviewData.userid);
            holder.created.setText(reviewData.created);
            holder.review.setText(reviewData.review);
            holder.star.setRating(reviewData.star);
            int count = reviewData.img.length;

            switch (count) {
                case 0:
                    holder.img_1.setVisibility(View.GONE);
                    holder.img_2.setVisibility(View.GONE);
                    holder.img_3.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.img_1.setImageResource(reviewData.img[0]);
                    holder.img_1.setVisibility(View.VISIBLE);
                    holder.img_2.setVisibility(View.GONE);
                    holder.img_3.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.img_1.setImageResource(reviewData.img[0]);
                    holder.img_1.setVisibility(View.VISIBLE);
                    holder.img_2.setImageResource(reviewData.img[1]);
                    holder.img_2.setVisibility(View.VISIBLE);
                    holder.img_3.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.img_1.setImageResource(reviewData.img[0]);
                    holder.img_1.setVisibility(View.VISIBLE);
                    holder.img_2.setImageResource(reviewData.img[1]);
                    holder.img_2.setVisibility(View.VISIBLE);
                    holder.img_3.setImageResource(reviewData.img[2]);
                    holder.img_3.setVisibility(View.VISIBLE);
                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");

                    StorageReference islandRef = rootReference.child(reviewData.img_url+"1");
                    System.out.println("이미지 출력 : " + reviewData.img_url+"1" );
                    Glide.with(mContext.getApplicationContext()).using(new FirebaseImageLoader())
                            .load(islandRef).thumbnail(0.1f).override(200,300)
                            .into(holder.img_2);
                    break;
            }
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
        arrayList.add(null);
        notifyItemInserted(arrayList.size()-1);
    }
    public void endProgress(){
        arrayList.remove(arrayList.size()-1);
        notifyItemRemoved(arrayList.size());
    }
    public class Data{
        public String market_text;
        public String replace_text;
        public String userid;
        public String created;
        public String review;
        public float star;
        public int[] img;
        public String img_url;
    }
}