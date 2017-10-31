package org.androidtown.sijang.FoodView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import org.androidtown.sijang.R;

import java.util.ArrayList;

public class SearchFoodMenuList extends AppCompatActivity
{
    private ArrayList<SearchFood> urlList = new ArrayList<SearchFood>();
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
        setImageLoader(options, config, this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.foodmenu_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("시장속 음식별 가게");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_back);

        SearchFood sf[][] = new SearchFood[6][10];
        sf[1][0] = new SearchFood("drawable://" + R.drawable.koreanfood1, "광장", "순희네빈대떡");
        sf[1][1] = new SearchFood("drawable://" + R.drawable.koreanfood2, "까치산", "다시오는순대국");
        sf[1][2] = new SearchFood("drawable://" + R.drawable.koreanfood3, "통인", "곽가네음식");
        sf[1][3] = new SearchFood("drawable://" + R.drawable.koreanfood4, "숭인", "동원한우소머리국밥");
        sf[1][4] = new SearchFood("drawable://" + R.drawable.koreanfood5, "대림", "봉희설렁탕");
        sf[1][5] = new SearchFood("drawable://" + R.drawable.koreanfood6, "영천", "정동국밥");
        sf[1][6] = new SearchFood("drawable://" + R.drawable.koreanfood7, "남부", "바우네나주곰탕");
        sf[1][7] = new SearchFood("drawable://" + R.drawable.koreanfood8, "양재", "하영호신촌설렁탕");
        sf[1][8] = new SearchFood("drawable://" + R.drawable.koreanfood9, "남부종합", "임금님밥상");
        sf[1][9] = new SearchFood("drawable://" + R.drawable.koreanfood10, "고덕전통", "소문난순대집");

        sf[2][0] = new SearchFood("drawable://" + R.drawable.chicken1, "화양", "장터치킨");
        sf[2][1] = new SearchFood("drawable://" + R.drawable.chicken2, "성대", "털보닭집");
        sf[2][2] = new SearchFood("drawable://" + R.drawable.chicken3, "청량리", "종구네통닭");
        sf[2][3] = new SearchFood("drawable://" + R.drawable.chicken4, "신원", "아저씨강정");

        sf[3][0] = new SearchFood("drawable://" + R.drawable.meat1, "인헌", "우리동네고깃집");
        sf[3][1] = new SearchFood("drawable://" + R.drawable.meat2, "성대", "시래불고기화풍정");
        sf[3][2] = new SearchFood("drawable://" + R.drawable.meat3, "가락", "한영상회");
        sf[3][3] = new SearchFood("drawable://" + R.drawable.meat4, "삼선", "MOCKBAR");
        sf[3][4] = new SearchFood("drawable://" + R.drawable.meat5, "삼선", "종로곱창");
        sf[3][5] = new SearchFood("drawable://" + R.drawable.meat6, "길음", "난이네곱창");
        sf[3][6] = new SearchFood("drawable://" + R.drawable.meat5, "공덕", "오향족발");
        sf[3][7] = new SearchFood("drawable://" + R.drawable.meat6, "영동", "늘푸른정육식당");

        sf[4][1] = new SearchFood("drawable://" + R.drawable.noodle1, "남부", "홍두깨부추칼국수" );
        sf[4][2] = new SearchFood("drawable://" + R.drawable.noodle2, "신성", "할머니손칼국수" );
        sf[4][3] = new SearchFood("drawable://" + R.drawable.noodle3, "동부", "짬뽕이네" );

        sf[5][1] = new SearchFood("drawable://" + R.drawable.bun1, "자양", "명동분식" );
        sf[5][2] = new SearchFood("drawable://" + R.drawable.bun2, "통인", "정할머니기름떡볶이" );
        sf[5][3] = new SearchFood("drawable://" + R.drawable.bun3, "도깨비", "동문샘터분식" );
        sf[5][4] = new SearchFood("drawable://" + R.drawable.bun4, "숭인", "제일분식" );
        sf[5][5] = new SearchFood("drawable://" + R.drawable.bun5, "가락", "골목분식집" );

        intent = getIntent();
        foodmenu = intent.getStringExtra("MenuSelect");

        switch(foodmenu){

            //전체보기 클릭시
            case "0" :
                urlList.add(sf[1][0]);
                urlList.add(sf[1][1]);
                urlList.add(sf[1][2]);
                urlList.add(sf[1][3]);
                urlList.add(sf[1][4]);
                urlList.add(sf[1][5]);
                urlList.add(sf[1][6]);
                urlList.add(sf[1][7]);
                urlList.add(sf[1][8]);
                urlList.add(sf[1][9]);

                urlList.add(sf[2][0]);
                urlList.add(sf[2][1]);
                urlList.add(sf[2][2]);
                urlList.add(sf[2][3]);

                urlList.add(sf[3][0]);
                urlList.add(sf[3][1]);
                urlList.add(sf[3][2]);
                urlList.add(sf[3][3]);
                urlList.add(sf[3][4]);
                urlList.add(sf[3][5]);
                urlList.add(sf[3][6]);
                urlList.add(sf[3][7]);

                urlList.add(sf[4][1]);
                urlList.add(sf[4][2]);
                urlList.add(sf[4][3]);

                urlList.add(sf[5][1]);
                urlList.add(sf[5][2]);
                urlList.add(sf[5][3]);
                urlList.add(sf[5][4]);
                urlList.add(sf[5][5]);

                break;

            // 한식 클릭시
            case "1" :
                urlList.add(sf[1][0]);
                urlList.add(sf[1][1]);
                urlList.add(sf[1][2]);
                urlList.add(sf[1][3]);
                urlList.add(sf[1][4]);
                urlList.add(sf[1][5]);
                urlList.add(sf[1][6]);
                urlList.add(sf[1][7]);
                urlList.add(sf[1][8]);
                urlList.add(sf[1][9]);

                break;

            // 치킨 클릭시
            case "2" :
                urlList.add(sf[2][0]);
                urlList.add(sf[2][1]);
                urlList.add(sf[2][2]);
                urlList.add(sf[2][3]);

                break;

            // 구이&회 클릭시
            case "3" :
                urlList.add(sf[3][0]);
                urlList.add(sf[3][1]);
                urlList.add(sf[3][2]);
                urlList.add(sf[3][3]);
                urlList.add(sf[3][4]);
                urlList.add(sf[3][5]);
                urlList.add(sf[3][6]);
                urlList.add(sf[3][7]);

                break;

            // 면류 클릭시
            case "4" :
                urlList.add(sf[4][1]);
                urlList.add(sf[4][2]);
                urlList.add(sf[4][3]);

                break;

            // 분식 클릭시
            case "5" :
                urlList.add(sf[5][1]);
                urlList.add(sf[5][2]);
                urlList.add(sf[5][3]);
                urlList.add(sf[5][4]);
                urlList.add(sf[5][5]);

                break;
        }


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
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final ViewHolder holder;
            if(convertView == null)
            {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.searchfoodmenulist_item, parent, false);

                holder.item_thumbnail = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageView_food_image_id);

                holder.where_imgbtn = (ImageView) convertView.findViewById(R.id.searchfoodmenulist_item_ImageBtn_where_id);

                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.where_imgbtn.setOnClickListener(new View.OnClickListener() {
                String s = "Tag";

                // 위치보기
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MenuInfo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Log.d(s, "as" + position);
                    intent.putExtra("market_key", urlList.get(position).marketname);
                    intent.putExtra("store_key", urlList.get(position).storename);

                    startActivity(intent);
                }

            });

            imageLoader.displayImage(urlList.get(position).url , holder.item_thumbnail, options);
            // imageLoader.displayImage(urlList.get(position), holder.item_thumbnail, options);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}