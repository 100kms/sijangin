package org.androidtown.sijang.MainView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.sijang.MarketView.MarketList;
import org.androidtown.sijang.R;

/**
 * Created by CYSN on 2017-10-21.
 */

public class Main_Category_Fragment extends Fragment {

    private GridView gv;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_category, container, false);

        int imgs[] = {
                R.drawable.marketpic, R.drawable.bun2, R.drawable.bun3, R.drawable.bun4,
                R.drawable.bun5, R.drawable.koreanfood1, R.drawable.koreanfood2, R.drawable.koreanfood3
        };

        String sijang1[] = {
                "도봉, 노원", "은평, 서대문", "성북, 종로", "중랑, 광진", "강서, 양천",
                "동작, 관악", "강남", "강동"
        };

        String sijang2[] = {
                "강북", "마포", "용산, 중구", "동대문 성동", "구로", "금천, 영등포",
                "서초", "송파"
        };

        GridAdapter adapter = new GridAdapter(getContext().getApplicationContext(), R.layout.grid_item, imgs, sijang1, sijang2);

        GridView gv = (GridView)view.findViewById(R.id.category_grid);
        gv.setAdapter(adapter);

        gv.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "도노강");
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "은서마");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "성종용중");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "중광동성");
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "강양구");
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "동관금영");
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "강서");
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getActivity().getApplication(), MarketList.class);
                        intent.putExtra("place", "강송");
                        startActivity(intent);
                        break;
                }
            }
        });

        return view;
    }

    public class GridAdapter extends BaseAdapter {

        Context context;
        int layout; //item xml
        int img[];
        String sijang1[], sijang2[];
        LayoutInflater inflater;

        GridAdapter(Context context, int layout, int[] img, String[] sijang1, String[] sijang2){
            this.context = context;
            this.layout = layout;
            this.img = img;
            this.sijang1 = sijang1;
            this.sijang2 = sijang2;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return img[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = inflater.inflate(layout, null);
            }
            ImageView iv = (ImageView)convertView.findViewById(R.id.grid_image);
            iv.setImageResource(img[position]);

            TextView tx1 = (TextView)convertView.findViewById(R.id.grid_text);
            TextView tx2 = (TextView)convertView.findViewById(R.id.grid_text2);
            tx1.setText(sijang1[position]);
            tx2.setText(sijang2[position]);

            return convertView;
        }

    }
}