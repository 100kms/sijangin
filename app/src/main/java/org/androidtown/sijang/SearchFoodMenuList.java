package org.androidtown.sijang;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchFoodMenuList extends AppCompatActivity {

    static SearchFoodMenuList_Adapter adapter2;
    static SearchFoodMenuList_Adapter adapter3;
    static ArrayList<SearchFoodMenuList_Item> data2;
    static ArrayList<SearchFoodMenuList_Item> data3;
    protected int position; // 메뉴위치(위에서부터 0번시작)
    //멍청이민수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfoodmenulist);

        Intent intent = getIntent();
        String i = intent.getStringExtra("MenuSelect");


        data2 = new ArrayList<SearchFoodMenuList_Item>();
        data3 = new ArrayList<SearchFoodMenuList_Item>();


        // data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a));
//
//        switch(i){
//            case "0" :
//                data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.store_name, R.drawable.where, R.drawable.market, "신성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.b, R.drawable.store_name, R.drawable.where, R.drawable.market, "한성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.c, R.drawable.store_name, R.drawable.where, R.drawable.market, "자양"));
//
//                break;
//            case "1" :
//                data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.store_name, R.drawable.where, R.drawable.market, "신성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.b, R.drawable.store_name, R.drawable.where, R.drawable.market, "한성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.c, R.drawable.store_name, R.drawable.where, R.drawable.market, "자양"));
//                break;
//            case "2" :
//                data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.store_name, R.drawable.where, R.drawable.market, "신성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.b, R.drawable.store_name, R.drawable.where, R.drawable.market, "한성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.store_image, R.drawable.store_name, R.drawable.where, R.drawable.market, "자양"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.store_name, R.drawable.where, R.drawable.market, "신성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.b, R.drawable.store_name, R.drawable.where, R.drawable.market, "한성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.store_image, R.drawable.store_name, R.drawable.where, R.drawable.market, "자양"));
//                break;
//            case "3" :
//                data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.store_name, R.drawable.where, R.drawable.market, "신성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.b, R.drawable.store_name, R.drawable.where, R.drawable.market, "한성"));
//                data2.add(new SearchFoodMenuList_Item(R.drawable.store_image, R.drawable.store_name, R.drawable.where, R.drawable.market, "자양"));
//                break;
//        }

        adapter2 = new SearchFoodMenuList_Adapter(getApplicationContext(), R.layout.searchfoodmenulist_item, data2);

        ListView lv = (ListView) findViewById(R.id.searchfoodmenulist_listView);

        lv.setAdapter(adapter2);


        // 검색버튼을 누를시 해당하는 시장만 목록에 뜨게
        ImageButton searchbtn = (ImageButton)findViewById(R.id.searchfoodmenulist_ImageBtn_search);

        searchbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // Thread2 th = new Thread2();

                EditText st = (EditText)findViewById(R.id.searchfoodmenulist_Edittext_search);
                String searchtext = st.getText().toString();

/*
                for(int i=0;  i < data2.size(); i++){
                    if(data2.get(i).mname == searchtext){
                        data3.add(new SearchFoodMenuList_Item(data2.get(i).mIcon_food, data2.get(i).mIcon_store, data2.get(i).mIcon_where, data2.get(i).mIcon_market, data2.get(i).mname));
                    }
                }

                adapter3 = new SearchFoodMenuList_Adapter(getApplicationContext(), R.layout.searchfoodmenulist_item, data3);

                ListView lv = (ListView) findViewById(R.id.InMenuList);

                //데이터가 수정될때 adpater.notifyDataSetChanged();

                adapter2.notifyDataSetChanged();
                lv.removeAllViews();
                adapter2.


                lv.setAdapter(adapter3);
*/


                //
                Intent searchintent = new Intent(getApplicationContext(), SearchFoodMenuList.class);

                switch (searchtext) {

                    case "신성":
                        searchintent.putExtra("SubMenuSelect", "0");
                        startActivity(searchintent);
                        break;
                    case "신성시장":
                        searchintent.putExtra("SubMenuSelect", "1");
                        startActivity(searchintent);
                        break;
                    case "자양":
                        searchintent.putExtra("SubMenuSelect", "2");
                        startActivity(searchintent);
                        break;
                    case "한성":
                        searchintent.putExtra("SubMenuSelect", "3");
                        startActivity(searchintent);
                        break;
                    default: //position의 값이 위에서 지정한 case에 하나도 맞지 않을경우
                        //실행코드
                        break;
                }
            }
        });


    }
}


/*
public class SearchFoodMenuList extends AppCompatActivity {

    static SearchFoodMenuList_Adapter adapter2;
    protected int position; // 메뉴위치(위에서부터 0번시작)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfoodmenulist);

        Intent intent = getIntent();
        String i = intent.getStringExtra("MenuSelect");

        // 검색버튼을 누를시 해당하는 시장만 목록에 뜨게
        ImageButton searchbtn = (ImageButton)findViewById(R.id.SearchButton);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText st = (EditText)findViewById(R.id.SearchText);
                String searchtext = st.getText().toString();

                Intent searchintent = new Intent(getApplicationContext(), SearchFoodMenuList.class);

                switch (searchtext) {

                    case "신성":
                        searchintent.putExtra("SubMenuSelect", "0");
                        startActivity(searchintent);
                        break;
                    case "신성시장":
                        searchintent.putExtra("SubMenuSelect", "1");
                        startActivity(searchintent);
                        break;
                    case "자양":
                        searchintent.putExtra("SubMenuSelect", "2");
                        startActivity(searchintent);
                        break;
                    case "자양시장":
                        searchintent.putExtra("SubMenuSelect", "3");
                        startActivity(searchintent);
                        break;
                    default: //position의 값이 위에서 지정한 case에 하나도 맞지 않을경우
                        //실행코드
                        break;
                }
            }
        });


        ArrayList<SearchFoodMenuList_Item> data2 = new ArrayList<SearchFoodMenuList_Item>();

        // data2.add(new SearchFoodMenuList_Item(R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a));

        switch(i){
            case "0" :

            case "1" :

            case "2" :

            case "3" :

                break;
        }

        adapter2 = new SearchFoodMenuList_Adapter(getApplicationContext(), R.layout.searchfoodmenulist_item, data2);

        ListView lv = (ListView) findViewById(R.id.InMenuList);

        lv.setAdapter(adapter2);

    }
}
*/