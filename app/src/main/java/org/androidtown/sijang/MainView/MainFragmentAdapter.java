package org.androidtown.sijang.MainView;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.androidtown.sijang.R;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-10-21.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder> {
    ArrayList<Integer> arrayList;
    Handler handler;
    Context context;
    public MainFragmentAdapter(Context context){
        this.context = context;
        arrayList = new ArrayList();
    }
    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public MainViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.fragment_main_ImgView);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    @Override
    public int getItemCount() {
        Log.i("kkkkk", "item : " + arrayList.size());
        return arrayList.size();
    }
    public  void addItem(int drawable){
        arrayList.add(drawable);
        notifyItemInserted(arrayList.size()-1);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(R.drawable.pic1).into(holder.imageView);
        holder.imageView.setAlpha(0.9f);

    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_frag_main_view_item, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }


}