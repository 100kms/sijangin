package org.androidtown.sijang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class SearchFoodMenuList extends AppCompatActivity
{
    private ArrayList<String> urlList = new ArrayList<String>();
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoaderConfiguration config = null;
    DisplayImageOptions options = null;

    Intent intent;
    String foodmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfoodmenulist);
        //setContentView(R.layout.fragment_list);
        setImageLoader(options, config, this);

        intent = getIntent();
        foodmenu = intent.getStringExtra("MenuSelect");

        switch(foodmenu){

            //전체보기 클릭시
            case "0" :
                urlList.add("drawable://" + R.drawable.koreanfood1);
                urlList.add("drawable://" + R.drawable.koreanfood2);
                urlList.add("drawable://" + R.drawable.koreanfood3);
                urlList.add("drawable://" + R.drawable.chicken1);
                urlList.add("drawable://" + R.drawable.chicken2);
                urlList.add("drawable://" + R.drawable.chicken3);
                urlList.add("drawable://" + R.drawable.chicken4);
                urlList.add("drawable://" + R.drawable.meat1);
                urlList.add("drawable://" + R.drawable.meat2);
                urlList.add("drawable://" + R.drawable.meat3);
                urlList.add("drawable://" + R.drawable.meat4);
                urlList.add("drawable://" + R.drawable.meat5);
                urlList.add("drawable://" + R.drawable.meat6);
                urlList.add("drawable://" + R.drawable.noodle1);
                urlList.add("drawable://" + R.drawable.noodle2);
                urlList.add("drawable://" + R.drawable.noodle3);
                urlList.add("drawable://" + R.drawable.bun1);
                urlList.add("drawable://" + R.drawable.bun2);
                urlList.add("drawable://" + R.drawable.bun3);
                urlList.add("drawable://" + R.drawable.bun4);
                break;

            // 한식 클릭시
            case "1" :
                urlList.add("drawable://" + R.drawable.koreanfood1);
                urlList.add("drawable://" + R.drawable.koreanfood2);
                urlList.add("drawable://" + R.drawable.koreanfood3);
                break;

            // 치킨 클릭시
            case "2" :
                urlList.add("drawable://" + R.drawable.chicken1);
                urlList.add("drawable://" + R.drawable.chicken2);
                urlList.add("drawable://" + R.drawable.chicken3);
                urlList.add("drawable://" + R.drawable.chicken4);
                break;

            // 구이&회 클릭시
            case "3" :
                urlList.add("drawable://" + R.drawable.meat1);
                urlList.add("drawable://" + R.drawable.meat2);
                urlList.add("drawable://" + R.drawable.meat3);
                urlList.add("drawable://" + R.drawable.meat4);
                urlList.add("drawable://" + R.drawable.meat5);
                urlList.add("drawable://" + R.drawable.meat6);
                break;

            // 면류 클릭시
            case "4" :
                urlList.add("drawable://" + R.drawable.noodle1);
                urlList.add("drawable://" + R.drawable.noodle2);
                urlList.add("drawable://" + R.drawable.noodle3);
                break;

            // 분식 클릭시
            case "5" :
                urlList.add("drawable://" + R.drawable.bun1);
                urlList.add("drawable://" + R.drawable.bun2);
                urlList.add("drawable://" + R.drawable.bun3);
                urlList.add("drawable://" + R.drawable.bun4);
                break;
        }

       // urlList.add("drawable://" + R.drawable.test6);

        /*
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
        urlList.add("http://imgnews.naver.net/image/thumb120/001/2014/10/12/7180761.jpg");
*/



        mListView = (ListView) findViewById(R.id.searchfoodmenulist_listView);
        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
    }


    private class ViewHolder
    {
        public ImageView item_thumbnail;
        public ImageView where_imgbtn;
        public ImageView market_imgbtn;
        //public TextView item_title;
    }

    public class ListViewAdapter extends BaseAdapter
    {
        Context context;

        public ListViewAdapter(Context context)
        {
            super();
            this.context = context;
        }


        @Override
        public int getCount()
        {
            return urlList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return position;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            final ViewHolder holder;
            if(convertView == null)
            {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.searchfoodmenulist_item, parent, false);

                holder.item_thumbnail = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageView_food_image_id);
                // holder.item_title = (TextView) convertView.findViewById(R.id.textView1);

                holder.where_imgbtn = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_where_id);
                holder.where_imgbtn.setOnClickListener(new View.OnClickListener() {
                    String s = "Tag";
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MenuInfo.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //Log.d(s, "as" + position);
                        startActivity(intent);
                    }

                });

                holder.market_imgbtn = (ImageView)convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_market_id);
                holder.market_imgbtn.setOnClickListener(new View.OnClickListener() {
                    String s = "Tag";

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MarketInfo.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //Log.d(s, "as" + position);
                        startActivity(intent);
                    }

                });


                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            imageLoader.displayImage(urlList.get(position), holder.item_thumbnail, options);
            //imageLoader.displayImage(urlList.get(position), holder.where_imgbtn, options);
            //imageLoader.displayImage(urlList.get(position), holder.market_imgbtn, options);
            //holder.item_title.setText(position+"");

            return convertView;
        }
    }

    public void setImageLoader(DisplayImageOptions options, ImageLoaderConfiguration config,Context context){
        options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .memoryCache(new WeakMemoryCache())
                .writeDebugLogs().build();

        options = new DisplayImageOptions.Builder().considerExifParams(true).cacheInMemory(true)
                .build();
        ImageLoader.getInstance().init(config);
    }
}