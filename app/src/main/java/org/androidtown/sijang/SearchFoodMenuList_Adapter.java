package org.androidtown.sijang;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;


public class SearchFoodMenuList_Adapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<SearchFoodMenuList_Item> mItems = new ArrayList<SearchFoodMenuList_Item>();

    public SearchFoodMenuList_Adapter(Context context, int resource, ArrayList<SearchFoodMenuList_Item> items) {
        mContext = context;
        mItems = items;
        mResource = resource;
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        ImageButton where_imgbtn;
        ImageButton market_imgbtn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;



        //Intent intent = new Intent(SearchFoodMenuList.MenuInfo.class);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();


            holder.where_imgbtn = (ImageButton) convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_where_id);
            holder.where_imgbtn.setOnClickListener(new View.OnClickListener() {
                String s = "Tag";
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, MenuInfo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d(s, "as" + position);
                    mContext.startActivity(intent);

                }

            });

            holder.market_imgbtn = (ImageButton)convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_market_id);
            holder.market_imgbtn.setOnClickListener(new View.OnClickListener() {
                String s = "Tag";

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, MarketInfo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d(s, "as" + position);
                    mContext.startActivity(intent);


                }

            });

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        // Set Icon
        //음식
        ImageView icon = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageView_food_image_id);
        icon.setImageResource(mItems.get(position).mIcon_food);

        /*
        //음식점이름
        ImageView icon2 = (ImageView) convertView.findViewById(R.id.store_name_id);
        icon2.setImageResource(mItems.get(position).mIcon_store);
        */

        //위치보기
        ImageView icon3 = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_where_id);
        icon3.setImageResource(mItems.get(position).mIcon_where);

        //시장
        ImageView icon4 = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_market_id);
        icon4.setImageResource(mItems.get(position).mIcon_market);

        return convertView;
    }
}

// 이미지 저장 클래스
class SearchFoodMenuList_Item {
    int mIcon_food; // 음식점의 음식
    //int mIcon_store; // 음식점 이름
    int mIcon_where; // 위치보기
    int mIcon_market; // 시장
    String mname;

    SearchFoodMenuList_Item(int food, int store, int where, int market, String name) {
        mIcon_food = food;
        //mIcon_store = store;
        mIcon_where = where;
        mIcon_market = market;
        mname = name;
    }
}
