package org.androidtown.sijang.MyinfoView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Favorite;
import org.androidtown.sijang.MarketView.MarketMainList;
import org.androidtown.sijang.R;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */

public class MarketRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext = null;
    private ArrayList<Favorite> arrayList;
    private final int NORMAL_TYPE = 1;
    private final int PROGRESS_TYPE = 2;
    private int progressPos = -1;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
    private FirebaseImageLoader firebaseImageLoader = new FirebaseImageLoader();
    private String image_index="0/";
    public MarketRecyclerAdapter(Context context){
        this.mContext = context;
        arrayList = new ArrayList<Favorite>();
    }
    public void addItem(Favorite favorite){
        arrayList.add(favorite);
    }
    public void addItem(Favorite favorite, String index){
        arrayList.add(favorite);
        image_index = index + "/";
    }
    public class ProgressViewHolder extends RecyclerView.ViewHolder{
        public ProgressBar progressBar;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.reviewProgressBar);
        }
    }
    public class DataViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout relativeLayout;
        public TextView marketName;
        public TextView user_id;
        public ImageView market_img;
        public Context context;
        public MarketRecyclerAdapter myRecyclerAdapter;

        public DataViewHolder(Context context, View itemView, MarketRecyclerAdapter myRecyclerAdapter) {
            super(itemView);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.market_layout);
            this.marketName = (TextView) itemView.findViewById(R.id.marketlist_item_text_name);
            this.market_img = (ImageView) itemView.findViewById(R.id.marketlist_item_image_market);
            this.user_id = (TextView) itemView.findViewById(R.id.marketlist_item_text_address);

            this.context = context;
            this.myRecyclerAdapter = myRecyclerAdapter;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof DataViewHolder) {
            DataViewHolder holder =  ((DataViewHolder)viewHolder);
            final Favorite favorite = arrayList.get(position);
            holder.marketName.setText(favorite.getMarketName());
            holder.user_id.setText(favorite.getAddress());

            StorageReference islandRef;
            islandRef = rootReference.child(favorite.getMarketName()+"/0.jpg");
            setImage(islandRef,holder.market_img);

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MarketMainList.class);
                    intent.putExtra("market_name",favorite.getMarketName());
                    intent.putExtra("경도", favorite.getLongitude());
                    intent.putExtra("위도", favorite.getLatitude());
                    intent.putExtra("주소", favorite.getAddress());
                    intent.putExtra("내용", favorite.getContent());
                    intent.putExtra("교통수단", favorite.getTraffic());
                    //기타값추가
                    mContext.startActivity(intent);
                }
            });

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
                view = inflater.inflate(R.layout.marketlist_item, parent, false);
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
            progressPos = -1;
        }
    }
}