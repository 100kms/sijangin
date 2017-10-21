package org.androidtown.sijang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
        String a = pref.getString("user_id", "");
        String b = pref.getString("user_name", "");
        Toast.makeText(getApplicationContext(), a + " : " + b, Toast.LENGTH_SHORT).show();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);

//        tabLayout = (TabLayout)findViewById(R.id.main_tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("홈"));
//        tabLayout.addTab(tabLayout.newTab().setText("지역별"));
//        tabLayout.addTab(tabLayout.newTab().setText("음식별"));
//        tabLayout.addTab(tabLayout.newTab().setText("리뷰"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(0);
    }
}

