package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FoodMenuList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodmenulist);

        final Button foodmenulist_btn1 = (Button)findViewById(R.id.foodmenulist_one);
        final Button foodmenulist_btn2 = (Button)findViewById(R.id.foodmenulist_two);
        final Button foodmenulist_btn3 = (Button)findViewById(R.id.foodmenulist_three);
        final Button foodmenulist_btn4 = (Button)findViewById(R.id.foodmenulist_four);
        final Button foodmenulist_btn5 = (Button)findViewById(R.id.foodmenulist_five);
        final Button foodmenulist_btn6 = (Button)findViewById(R.id.foodmenulist_six);




        foodmenulist_btn1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    foodmenulist_btn1.setBackgroundResource(R.drawable.foodmenulist_bob_change);
                } else if(action==MotionEvent.ACTION_UP){
                    foodmenulist_btn1.setBackgroundResource(R.drawable.foodmenulist_bob);
                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                    intent.putExtra("MenuSelect", "0");
                    startActivity(intent);
                }
                return true;
            }});




        foodmenulist_btn2.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    foodmenulist_btn2.setBackgroundResource(R.drawable.foodmenulist_chi_change);
                } else if(action==MotionEvent.ACTION_UP){
                    foodmenulist_btn2.setBackgroundResource(R.drawable.foodmenulist_chi);
                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                    intent.putExtra("MenuSelect", "1");
                    startActivity(intent);}
                return true;
            }});



        foodmenulist_btn3.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    foodmenulist_btn3.setBackgroundResource(R.drawable.foodmenulist_fork_change);
                } else if(action==MotionEvent.ACTION_UP){
                    foodmenulist_btn3.setBackgroundResource(R.drawable.foodmenulist_fork);
                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                    intent.putExtra("MenuSelect", "2");
                    startActivity(intent);}
                return true;
            }});




        foodmenulist_btn4.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    foodmenulist_btn4.setBackgroundResource(R.drawable.foodmenulist_mid_change);
                } else if(action==MotionEvent.ACTION_UP){
                    foodmenulist_btn4.setBackgroundResource(R.drawable.foodmenulist_mid);
                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                    intent.putExtra("MenuSelect", "3");
                    startActivity(intent);
                }
                return true;
            }});



        foodmenulist_btn5.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    foodmenulist_btn5.setBackgroundResource(R.drawable.foodmenulist_beun_change);
                } else if(action==MotionEvent.ACTION_UP){
                    foodmenulist_btn5.setBackgroundResource(R.drawable.foodmenulist_beun);
                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                    intent.putExtra("MenuSelect", "4");
                    startActivity(intent);}
                return true;
            }});



        foodmenulist_btn6.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    foodmenulist_btn6.setBackgroundResource(R.drawable.foodmenulist_etc_change);
                } else if(action==MotionEvent.ACTION_UP){
                    foodmenulist_btn6.setBackgroundResource(R.drawable.foodmenulist_etc);
                    Intent intent = new Intent(getApplicationContext(), SearchFoodMenuList.class);
                    intent.putExtra("MenuSelect", "5");
                    startActivity(intent);}
                return true;
            }});




        //타이틀바 삭제
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
}



