package org.androidtown.sijang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
//
    //ImageButton button = (ImageButton)findViewById(R.id.imageButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            System.out.println("쓰기권한이 없었네 ????" );
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 466);
        }

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            System.out.println("리드권한이 없었네 ????" );
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            }, 466);
        }

        final ImageButton pop_btn = (ImageButton)findViewById(R.id.imageButton2);
        final ImageButton food_btn = (ImageButton)findViewById(R.id.imageButton3);
        final ImageButton review_btn = (ImageButton)findViewById(R.id.imageButton4);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
//        TextView textView = (TextView) findViewById(R.id.textView);
//        Typeface face =Typeface.createFromAsset(getAssets(), "fonts/bfont.ttf");
//        textView3.setTypeface(face);
//        textView.setTypeface(face);
//
//        ////////////////////////////실험
//        findViewById(R.id.imageButton8).setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        editText = (EditText)findViewById(R.id.editText);
//                        marketname = editText.getText().toString();
//                        Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
//                        intent.putExtra("value","1번째 페이지");
//                        // intent.putExtra("value",textView.getText().toString());
//
//                        startActivity(intent);
//                    }
//                }
//        );
//        findViewById(R.id.imageButton9).setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        editText = (EditText)findViewById(R.id.editText);
//                        marketname = editText.getText().toString();
//                        Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
//                        intent.putExtra("value","2번째 페이지");
//                        // intent.putExtra("value",textView.getText().toString());
//                        startActivity(intent);
//                    }
//                }
//        );
//        findViewById(R.id.imageButton10).setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//                        editText = (EditText)findViewById(R.id.editText);
//                        marketname = editText.getText().toString();
//                        Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
//                        intent.putExtra("value","3번째 페이지");
//                        // intent.putExtra("value",textView.getText().toString());
//                        startActivity(intent);
//                    }
//                }
//        );

        //   ////////////////////////////실험



        //인기시장 버튼
        /*
        pop_btn.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    pop_btn.setImageResource(R.drawable.popular_change);
                } else if(action==MotionEvent.ACTION_UP){
                    pop_btn.setImageResource(R.drawable.popular);
                    Intent intent = new Intent(getApplicationContext(), FireActivity.class);
                    startActivity(intent);}
                return true;
            }});
        */

        pop_btn.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    pop_btn.setImageResource(R.drawable.popular_change);
                } else if(action==MotionEvent.ACTION_UP){
                    pop_btn.setImageResource(R.drawable.popular);
                    Intent intent = new Intent(getApplicationContext(), Review_Write.class);
                    startActivity(intent);
                }
                return true;
            }});



        //음식버튼
        food_btn.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    food_btn.setImageResource(R.drawable.food_change);
                } else if(action==MotionEvent.ACTION_UP){
                    food_btn.setImageResource(R.drawable.food);
                    Intent intent = new Intent(getApplicationContext(), FoodMenuList.class);
                    startActivity(intent);}
                return true;
            }});


        //리뷰버튼
        review_btn.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();

                if(action==MotionEvent.ACTION_DOWN) {
                    review_btn.setImageResource(R.drawable.review_change);
                } else if(action==MotionEvent.ACTION_UP){
                    review_btn.setImageResource(R.drawable.review);
                    Intent intent = new Intent(getApplicationContext(), ReviewList.class);
                    startActivity(intent);}
                return true;
            }});
    }


}

