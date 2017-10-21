package org.androidtown.sijang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.util.ArrayList;
import java.util.List;

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
        return arrayList.size();
    }
    public  void addItem(int drawable){
        arrayList.add(drawable);
        notifyItemInserted(arrayList.size()-1);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(arrayList.get(position)).into(holder.imageView);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_main_view_item, parent, false);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }


}
