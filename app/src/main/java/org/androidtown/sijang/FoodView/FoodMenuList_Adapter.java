package org.androidtown.sijang.FoodView;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.androidtown.sijang.R;

import java.util.ArrayList;


public class FoodMenuList_Adapter extends BaseAdapter {


    private Context mContext;
    private int mResource;
    private ArrayList<FoodMenuList_Item> mItems = new ArrayList<FoodMenuList_Item>();

    public FoodMenuList_Adapter(Context context, int resource, ArrayList<FoodMenuList_Item> items) {
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

    static class ViewHolder{
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent,false);

            holder.image = (ImageView)convertView.findViewById(R.id.foodmenulist_Imageview_ImageIcon);
            //holder.image.setImageResource(mItems.get(position).mBitmap);
            holder.image.setImageBitmap(mItems.get(position).mBitmap);
            //holder.image.loadUrl("D:\\MenuSelect\\app\\src\\main\\res\\drawable\\a.jpg");
            //holder.image.setBackgroundResource(mItems.get(position).mIcon);
            //holder.image.loadUrl(mItems.get(position).mIcon);
            // holder.image.setFocusable(false);
            //holder.image.setBackground(null);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        /*
        // Set Icon
        ImageView icon = (ImageView) convertView.findViewById(R.id.SearchMenuIcon);
        icon.setImageResource(mItems.get(position).mIcon);
        */
        return convertView;
    }
}

class FoodMenuList_Item {

    /*
    String mIcon; // image resource

    SearchMenuItem(String aIcon) {
        mIcon = aIcon;

    }
    */
    Bitmap mBitmap;
    int mIcon; // image resource


    public FoodMenuList_Item(Bitmap b) {
        //mIcon = aIcon;
        mBitmap = b;
    }

}
