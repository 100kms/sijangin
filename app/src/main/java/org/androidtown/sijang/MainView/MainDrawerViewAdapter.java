package org.androidtown.sijang.MainView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        ImageView imageView;
        LinearLayout linearLayout;
        ImageView right_imageView;
        public DrawerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.drawer_main_textView);
            imageView = (ImageView) itemView.findViewById(R.id.drawer_main_imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.drawer_main_LinearLayout);
            right_imageView = (ImageView) itemView.findViewById(R.id.drawer_main_right_imageView);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action=event.getAction();
                    if(action == MotionEvent.ACTION_MOVE && v.isPressed()) {
                        linearLayout.setBackgroundColor(Color.parseColor("#c6c4c4"));
                        imageView.setBackgroundColor(Color.parseColor("#c6c4c4"));
                        textView.setBackgroundColor(Color.parseColor("#c6c4c4"));
                        right_imageView.setBackgroundColor(Color.parseColor("#c6c4c4"));
                    }
                    else{

                        linearLayout.setBackgroundColor(Color.parseColor("#ffffffff"));
                        imageView.setBackgroundColor(Color.parseColor("#ffffffff"));
                        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
                        right_imageView.setBackgroundColor(Color.parseColor("#ffffffff"));
                    }
                    return false;
                }
            });
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("message",1);
            String data = textView.getText().toString();
            bundle.putString("data",data);
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
        if(arrayList.get(position).equals(MainActivity.drawerMneu[0])){//내정보
            holder.imageView.setImageResource(R.drawable.main_drawer_info);
        }
        else if(arrayList.get(position).equals(MainActivity.drawerMneu[1])){//공지사항
            holder.imageView.setImageResource(R.drawable.main_drawer_notice);
        }
        else if(arrayList.get(position).equals(MainActivity.drawerMneu[2])){//시장IN정보
            holder.imageView.setImageResource(R.drawable.main_drawer_sijang);
        }
        else if(arrayList.get(position).equals(MainActivity.drawerMneu[3])){//로그아웃
            holder.imageView.setImageResource(R.drawable.main_drawer_exit);
        }
    }

    @Override
    public MainDrawerViewAdapter.DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.adapter_drawer_main_view_item, parent, false);
        DrawerViewHolder drawerViewHolder = new DrawerViewHolder(view);
        return drawerViewHolder;
    }


}