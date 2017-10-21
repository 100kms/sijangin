package org.androidtown.sijang.MyinfoView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidtown.sijang.R;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-09-26.
 */
//Favorite Market
public class MarketRecyclerAdapter extends RecyclerView.Adapter<MarketRecyclerAdapter.ViewHolder> {
    private Context mContext = null;
    private ArrayList<Data> arrayList;

    public MarketRecyclerAdapter(Context context) {
        this.mContext = context;
        arrayList = new ArrayList<Data>();
    }

    public void additem(int market_image, String market_name) {
        Data addinfo = new Data();
        addinfo.market_image = market_image;
        addinfo.market_name = market_name;

        arrayList.add(addinfo);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        public ImageView market_image;
        public TextView market_name;
        public Context context;
        public Button delete_button;
        public MarketRecyclerAdapter marketRecyclerAdapter;

        public ViewHolder(Context context, View itemView, MarketRecyclerAdapter marketRecyclerAdapter) {
            super(itemView);
            this.market_image = (ImageView) itemView.findViewById(R.id.marketlist_item_image_market);
            this.market_name = (TextView) itemView.findViewById(R.id.marketlist_item_text_name);
            this.delete_button = (Button) itemView.findViewById(R.id.market_deleteBtn);
            this.context = context;
            this.marketRecyclerAdapter = marketRecyclerAdapter;
            delete_button.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            marketRecyclerAdapter.removeItem(position);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data marketData = arrayList.get(position);

        Glide.with(mContext.getApplicationContext()).load(marketData.market_image).override(100,100).thumbnail(0.1f).into(holder.market_image);
        /*Glide.with(mContext)
                .load(marketData.market_image).into(holder.market_image);*/
        // holder.market_image.setImageDrawable(marketData.market_image);
        holder.market_name.setText(marketData.market_name);


    }

    @Override
    public int getItemViewType(int position) {  // position에 해당하는 viewtype을 리턴
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void removeItem(int p) {
        arrayList.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, getItemCount() - p);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.marketlist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, view, this);
        return viewHolder;

    }

    public class Data {
        public int market_image;
        public String market_name;

    }
}