package org.androidtown.sijang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    MainFragment mainFragment2;
    MainFragment mainFragment3;
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

       tabLayout = (TabLayout)findViewById(R.id.main_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("홈"));
        tabLayout.addTab(tabLayout.newTab().setText("지역별"));
        tabLayout.addTab(tabLayout.newTab().setText("음식별"));
//        tabLayout.addTab(tabLayout.newTab().setText("리뷰"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        mainFragment = new MainFragment();
        mainFragment2 = new MainFragment();
        mainFragment3 = new MainFragment();
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

    }
    public class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position){
                case 0 : return mainFragment;
                case 1 : return  mainFragment2;
                case 2 : return  mainFragment3;
            }
            return null;
        }
    }
}
