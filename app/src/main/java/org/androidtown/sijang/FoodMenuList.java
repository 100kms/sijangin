package org.androidtown.sijang;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FoodMenuList extends AppCompatActivity {

    ArrayList<FoodMenuList_Item> data;
    ListView lv;
    static FoodMenuList_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmenulist);

        data = new ArrayList<FoodMenuList_Item>();

        // String s = "1";
        //타이틀바 삭제
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.d("1", "1");
        MyAsyncTask asyncTask = new MyAsyncTask();
        Log.d("2", "2");
        asyncTask.execute(data);

        Log.d("3", "3");
    }


    public class MyAsyncTask extends AsyncTask<ArrayList<FoodMenuList_Item>, ArrayList<FoodMenuList_Item>, ArrayList<FoodMenuList_Item>> {

        //doInbackground 메소드가 실행되기전 실행되는 메소드이다. 비동기 처리전에 무엇인가 처리를 하고 싶다면 사용
        protected void onPreExecute() {

        }

        // 처리하고 싶은 내용
        protected ArrayList<FoodMenuList_Item> doInBackground(ArrayList<FoodMenuList_Item>... params) {
            Log.d("4", "4");
            data = params[0];

            try {
                System.out.print("kkkkkkkkkk");

                /*
                data.add(new SearchMenuItem("http://postfiles3.naver.net/MjAxNzA3MDlfMTgz/MDAxNDk5NTk2MDQzNTY4.dcqXJ2l082zDIAwWcY8wW8YqvW2j2doV3k-Ydgdr7Lkg.pGBZhhjqO6EIr5qsVZQZOCAyhxHNsvZOsBMQo3Quu58g.JPEG.h7678036/b.jpg?type=w2"));
                data.add(new SearchMenuItem("http://postfiles1.naver.net/MjAxNzA3MDlfOTgg/MDAxNDk5NTk2MDI4NDU4.EORNoqQdI72ZfxlMaewg5mtoLm6iEZeU0QnXDRiQO_Mg.9SO9F8yBL3Uf_ZHnFWPChscvJR8Prx68RVhoIqd6mI0g.JPEG.h7678036/a.jpg?type=w2"));
                data.add(new SearchMenuItem("http://postfiles13.naver.net/MjAxNzA3MDlfMjEg/MDAxNDk5NTk2ODM2ODI4.XBzWfFiMfGO77qYMbM6dLYknW-1vG_v2mmD2Pf8YU44g.hfhytFkOypfSyplaUAapOKzb86wVSqpDIa61MEQjmgcg.JPEG.h7678036/c.jpg?type=w2"));

                data.add(new SearchMenuItem("http://postfiles3.naver.net/MjAxNzA3MDlfMTgz/MDAxNDk5NTk2MDQzNTY4.dcqXJ2l082zDIAwWcY8wW8YqvW2j2doV3k-Ydgdr7Lkg.pGBZhhjqO6EIr5qsVZQZOCAyhxHNsvZOsBMQo3Quu58g.JPEG.h7678036/b.jpg?type=w2"));
                data.add(new SearchMenuItem("http://postfiles1.naver.net/MjAxNzA3MDlfOTgg/MDAxNDk5NTk2MDI4NDU4.EORNoqQdI72ZfxlMaewg5mtoLm6iEZeU0QnXDRiQO_Mg.9SO9F8yBL3Uf_ZHnFWPChscvJR8Prx68RVhoIqd6mI0g.JPEG.h7678036/a.jpg?type=w2"));
                data.add(new SearchMenuItem("http://postfiles13.naver.net/MjAxNzA3MDlfMjEg/MDAxNDk5NTk2ODM2ODI4.XBzWfFiMfGO77qYMbM6dLYknW-1vG_v2mmD2Pf8YU44g.hfhytFkOypfSyplaUAapOKzb86wVSqpDIa61MEQjmgcg.JPEG.h7678036/c.jpg?type=w2"));

                data.add(new SearchMenuItem("http://postfiles3.naver.net/MjAxNzA3MDlfMTgz/MDAxNDk5NTk2MDQzNTY4.dcqXJ2l082zDIAwWcY8wW8YqvW2j2doV3k-Ydgdr7Lkg.pGBZhhjqO6EIr5qsVZQZOCAyhxHNsvZOsBMQo3Quu58g.JPEG.h7678036/b.jpg?type=w2"));
                data.add(new SearchMenuItem("http://postfiles1.naver.net/MjAxNzA3MDlfOTgg/MDAxNDk5NTk2MDI4NDU4.EORNoqQdI72ZfxlMaewg5mtoLm6iEZeU0QnXDRiQO_Mg.9SO9F8yBL3Uf_ZHnFWPChscvJR8Prx68RVhoIqd6mI0g.JPEG.h7678036/a.jpg?type=w2"));
                data.add(new SearchMenuItem("http://postfiles13.naver.net/MjAxNzA3MDlfMjEg/MDAxNDk5NTk2ODM2ODI4.XBzWfFiMfGO77qYMbM6dLYknW-1vG_v2mmD2Pf8YU44g.hfhytFkOypfSyplaUAapOKzb86wVSqpDIa61MEQjmgcg.JPEG.h7678036/c.jpg?type=w2"));
                */
                /*
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
                data.add(new SearchMenuItem(R.drawable.a));
*/
                Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.food);
                Bitmap bit2 = BitmapFactory.decodeResource(getResources(), R.drawable.map);
                Bitmap bit3 = BitmapFactory.decodeResource(getResources(), R.drawable.popular);
                Bitmap bit4 = BitmapFactory.decodeResource(getResources(), R.drawable.review);

                data.add(new FoodMenuList_Item(bit));
                data.add(new FoodMenuList_Item(bit2));
                data.add(new FoodMenuList_Item(bit3));
                data.add(new FoodMenuList_Item(bit4));
                data.add(new FoodMenuList_Item(bit));
                data.add(new FoodMenuList_Item(bit2));
                data.add(new FoodMenuList_Item(bit3));
                data.add(new FoodMenuList_Item(bit4));



                //bit.recycle();
                //bit2.recycle();
                //bit3.recycle();
                // bit4.recycle();

                adapter = new FoodMenuList_Adapter(getApplicationContext(), R.layout.foodmenulist_item, data);

                //lv = (ListView) findViewById(R.id.foodmenulist_listview);

            } catch (Exception e) {
                System.out.print("kkk222222");
                e.printStackTrace();
            }
            Log.d("5", "5");
            return null;
        }

        // 비동기 처리의 진행 상황을 진행률로 표시하고 싶을 때 등 에서 사용, 백그라운드에 호출되는 경우 처리된다.
        protected void onProgressUpdate() {

        }

        // doInBackground 메소드 후에 실행되는 마지막 메소드이다. 백그라운드 메소드의 반환값을 인자로 받아 그결과를 화면에 반영 할 수 있다.
        protected void onPostExecute(ArrayList<FoodMenuList_Item> s) {
            Log.d("6", "6");
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //리스트뷰의 아이템 (한 행)이 클릭된 경우를 처리
                    // 클릭이 되었을 때 넘어오는 패러미터 중 position이라는 이름을 가진 int타입의 변수는
                    // 리스트뷰에서 몇번째 아이템이 클릭되었는지 숫자로 나타냄

                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);

                    switch (position) {

                        case 0: // position의 값이 0일 경우 = 맨 처음꺼
                            // 인텐트에 값을 실어 보냄
                            intent.putExtra("MenuSelect", "0");
                            startActivity(intent);
                            break;
                        case 1:
                            intent.putExtra("MenuSelect", "1");
                            startActivity(intent);
                            break;
                        case 2:
                            intent.putExtra("MenuSelect", "2");
                            startActivity(intent);
                            break;
                        case 3:
                            intent.putExtra("MenuSelect", "3");
                            startActivity(intent);
                            break;
                        default: //position의 값이 위에서 지정한 case에 하나도 맞지 않을경우
                            //실행코드
                            break;
                    }

                }
            });
        }

    }
}
