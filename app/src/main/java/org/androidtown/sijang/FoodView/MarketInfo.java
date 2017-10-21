package org.androidtown.sijang.FoodView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidtown.sijang.R;

public class MarketInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_info);

        Intent intent = getIntent();
        String i = intent.getStringExtra("SubMenuSelect");

        TextView tv = (TextView)findViewById(R.id.textview4);
        tv.setText("시장나오는곳" + i);

    }
}
