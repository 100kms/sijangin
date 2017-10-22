package org.androidtown.sijang.MainView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;

import org.androidtown.sijang.MarketView.MarketList;
import org.androidtown.sijang.MyInfoActivity;
import org.androidtown.sijang.R;


public class MainActivity extends AppCompatActivity {

    Main_RankFragment mainFragment;
    MainFragment mainFragment2;
    MainFragment mainFragment3;
    MainMapFragment mainFragment4;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    public static String[] drawerMneu = {
            "내 정보 보기",
            "공지사항",
            "시장IN 정보",
            "로그아웃",
    };//main_drawer_listView
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle toggle = null;
    private RecyclerView recyclerView;
    private MainDrawerViewAdapter mainDrawerViewAdapter;
    private MaterialMenuDrawable materialMenu;
    private boolean isDrawerOpened = false;
    private int              actionBarMenuState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.main_drawer_recycler_view);
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        linearLayout = (LinearLayout)findViewById(R.id.main_drawer_linear_layout);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int message = msg.getData().getInt("message");
                switch (message){
                    case 1: {//close drawer
                        drawerLayout.closeDrawer(linearLayout);
                        String data = msg.getData().getString("data");
                        Intent intent = null;
                        if (data.equals(drawerMneu[0])) {//myMenu
                            intent = new Intent(getApplicationContext(), MyInfoActivity.class);
                            startActivity(intent);
                        } else if (data.equals(drawerMneu[1])) {// "공지사항",
                             intent = new Intent(getApplicationContext(), MarketList.class);
                            startActivity(intent);
                        } else if (data.equals(drawerMneu[2])) {//   "시장IN 정보",

                        } else if (data.equals(drawerMneu[3])) {//"로그아웃",

                        } else {
                            Toast.makeText(getApplicationContext(), "Message Error", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    }
                    case 4 : {
                        String data = msg.getData().getString("data");
                    }
                }
            }
        };


        mainDrawerViewAdapter = new MainDrawerViewAdapter(getApplicationContext(), handler);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mainDrawerViewAdapter);

        for(int i=0; i<drawerMneu.length; i++){
            mainDrawerViewAdapter.addItem(drawerMneu[i]);
            Log.i("kkkkkk",drawerMneu[i]);
        }


        SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
        String a = pref.getString("user_id", "");
        String b = pref.getString("user_name", "");
        Toast.makeText(getApplicationContext(), a + " : " + b, Toast.LENGTH_SHORT).show();

     Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name, R.string.app_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.main_menu_drawable);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout.addDrawerListener(toggle);



        tabLayout = (TabLayout)findViewById(R.id.main_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("추천"));
        tabLayout.addTab(tabLayout.newTab().setText("카테고리"));
        tabLayout.addTab(tabLayout.newTab().setText("전체 리뷰"));
        tabLayout.addTab(tabLayout.newTab().setText("가까운 시장"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        mainFragment = new Main_RankFragment();
        mainFragment2 = new MainFragment();
        mainFragment3 = new MainFragment();
        mainFragment4 = new MainMapFragment();
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position){
                case 0 : return mainFragment;
                case 1 : return  mainFragment2;
                case 2 : return  mainFragment3;
                case 3 : return  mainFragment4;
            }
            return null;
        }
    }
}