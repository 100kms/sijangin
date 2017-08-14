package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //ImageButton button = (ImageButton)findViewById(R.id.imageButton);
    EditText editText;
    String marketname="미입력";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageButton).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        editText = (EditText)findViewById(R.id.editText);
                        marketname = editText.getText().toString();
                        Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
                        intent.putExtra("value",marketname);
                        // intent.putExtra("value",textView.getText().toString());
                        startActivity(intent);
                    }
                }
        );

        ImageButton pop_btn = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton food_btn = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton review_btn = (ImageButton)findViewById(R.id.imageButton4);



        food_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), FoodMenuList.class);
                    startActivity(intent);
            }
        });

        //리뷰버튼
        review_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReviewList.class);
                startActivity(intent);
            }
        });

    }

    /*Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            //버튼 클릭시 일어날 일
           // marketname = textView.getText().toString();
            Intent intent = new Intent(MainActivity.this, ChangeActivity.class);
           // intent.putExtra("value",marketname);
            intent.putExtra("value",textView.getText().toString());
            startActivity(intent);
        }
    };*/

}

