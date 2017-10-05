package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MarketMainList extends MainActivity {

    private ListView mainreview = null;
    private MainReviewList_Adapter mainreview_adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketmainlist);

        Button write = (Button)findViewById(R.id.marketmainlist_btn_review);

        write.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketMainList.this, Review_Write.class);
                startActivity(intent);
            }
        });

        mainreview = (ListView)findViewById(R.id.marketmainlist_listview_review);
        mainreview_adapter = new MainReviewList_Adapter(this);
        mainreview.setAdapter(mainreview_adapter);

        setListViewHeightBasedOnChildren(mainreview);

        int[] img = {};
        int[] img1 = {R.drawable.img1, R.drawable.img2};
        int[] img2 = {R.drawable.hyuk1};
        int[] img3 = {R.drawable.img1, R.drawable.img2, R.drawable.img3};

        mainreview_adapter.additem("자양시장", "해남치킨","keealsgur","2017-07-24","너무너무 맛있었습니다. 여러분 많이오세요!!!!!!!",3.5f, img2);
        mainreview_adapter.additem("광장시장", "맛나파전", "alstnqkqh","2017-07-24","민수바보",3.5f, img1);

        final int[] count = {0};

        setImage(count[0]);
    }

    public void setImage(int count){

        ImageView img_info = (ImageView)findViewById(R.id.marketmainlist_image_main);

        switch (count){
            case 0 :
                img_info.setImageResource(R.drawable.img1);
                break;
            case 1 :
                img_info.setImageResource(R.drawable.img2);
                break;
            case 2 :
                img_info.setImageResource(R.drawable.img3);
                break;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }

        int totalHeight = 0;
        for(int i = 0; i<listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}