package org.androidtown.sijang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ChangeActivity extends AppCompatActivity {

    //aabb_prettyje
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Intent intent=new Intent(this.getIntent());
        String s=intent.getStringExtra("value");
        TextView textView=(TextView)findViewById(R.id.textView2);
        textView.setText(s);
    }
}
