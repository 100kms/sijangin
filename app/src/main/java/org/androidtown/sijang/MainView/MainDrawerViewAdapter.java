package org.androidtown.sijang.MainView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.sijang.R;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-10-21.
 */

public class MainDrawerViewAdapter extends RecyclerView.Adapter<MainDrawerViewAdapter.DrawerViewHolder>{
    private ArrayList<String> arrayList = new ArrayList<>();
    private Context context;
    private Handler handler;
    public MainDrawerViewAdapter(Context context,Handler handler){
        this.context = context;
        this.handler = handler;
        arrayList = new ArrayList();
    }
    class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public DrawerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.drawer_main_textView);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("message",1);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    }
    public void addItem(String name){
        arrayList.add(name);
        notifyItemInserted(arrayList.size()-1);
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(MainDrawerViewAdapter.DrawerViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
    }

    @Override
    public MainDrawerViewAdapter.DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_drawer_main_view_item, parent, false);
        DrawerViewHolder drawerViewHolder = new DrawerViewHolder(view);
        return drawerViewHolder;
    }


}