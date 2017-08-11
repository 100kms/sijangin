package org.androidtown.sijang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class MarketMainList extends MainActivity {

    private ListView food_listview = null;
    private MarketMainList_Adapter marketMainList_adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketmainlist);

        final int[] count = {0};

        Button btn_info = (Button)findViewById(R.id.marketmainlist_btn_info);
        Button btn_way = (Button)findViewById(R.id.marketmainlist_btn_way);
        Button btn_food = (Button)findViewById(R.id.marketmainlist_btn_food);

        Button btn_next = (Button)findViewById(R.id.marketmainlist_btn_next);
        Button btn_prev = (Button)findViewById(R.id.marketmainlist_btn_prev);

        setImage(count[0]);

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                count[0] ++;
                if(count[0]==3)
                    count[0] = 0;
                setImage(count[0]);
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(count[0]==0)
                    count[0] = 3;
                count[0]--;
                setImage(count[0]);
            }
        });

        final RelativeLayout layout_info = (RelativeLayout)findViewById(R.id.marketmainlist_layout_info);
        final RelativeLayout layout_way = (RelativeLayout)findViewById(R.id.marketmainlist_layout_way);
        final RelativeLayout layout_food = (RelativeLayout)findViewById(R.id.marketmainlist_layout_food);

        food_listview = (ListView)findViewById(R.id.marketmainlist_listview_food);
        marketMainList_adapter = new MarketMainList_Adapter(this);
        food_listview.setAdapter(marketMainList_adapter);

        marketMainList_adapter.addItem("땅땅치킨");
        marketMainList_adapter.addItem("짱짱족발");
        marketMainList_adapter.addItem("부어치킨");

        layout_food.setVisibility(View.INVISIBLE);

        btn_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layout_info.setVisibility(View.VISIBLE);
                layout_way.setVisibility(View.INVISIBLE);
                layout_food.setVisibility(View.INVISIBLE);
            }
        });

        btn_way.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layout_info.setVisibility(View.INVISIBLE);
                layout_way.setVisibility(View.VISIBLE);
                layout_food.setVisibility(View.INVISIBLE);
            }
        });

        btn_food.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layout_info.setVisibility(View.INVISIBLE);
                layout_way.setVisibility(View.INVISIBLE);
                layout_food.setVisibility(View.VISIBLE);
            }
        });
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
}
