package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FoodMenuList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmenulist);

        Button foodmenulist_btn1 = (Button)findViewById(R.id.foodmenulist_one);
        Button foodmenulist_btn2 = (Button)findViewById(R.id.foodmenulist_two);
        Button foodmenulist_btn3 = (Button)findViewById(R.id.foodmenulist_three);
        Button foodmenulist_btn4 = (Button)findViewById(R.id.foodmenulist_four);
        Button foodmenulist_btn5 = (Button)findViewById(R.id.foodmenulist_five);
        Button foodmenulist_btn6 = (Button)findViewById(R.id.foodmenulist_six);

        // String s = "1";

        foodmenulist_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                intent.putExtra("MenuSelect", "0");
                startActivity(intent);
            }
        });

        foodmenulist_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                intent.putExtra("MenuSelect", "1");
                startActivity(intent);
            }
        });

        foodmenulist_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                intent.putExtra("MenuSelect", "2");
                startActivity(intent);
            }
        });

        foodmenulist_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                intent.putExtra("MenuSelect", "3");
                startActivity(intent);
            }
        });

        foodmenulist_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                intent.putExtra("MenuSelect", "4");
                startActivity(intent);
            }
        });

        foodmenulist_btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                intent.putExtra("MenuSelect", "5");
                startActivity(intent);
            }
        });
        //타이틀바 삭제
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}



