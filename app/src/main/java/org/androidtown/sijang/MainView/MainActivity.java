package org.androidtown.sijang.MainView;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;

import org.androidtown.sijang.R;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    MainFragment mainFragment2;
    MainFragment mainFragment3;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private String[] drawerMneu = {
            "내 정보 보기",
            "공지사항",
            "로그아웃",
            "내 정보 보기",
            "공지사항",
            "로그아웃",
            "내 정보 보기",
            "공지사항",
            "로그아웃",
    };//main_drawer_listView
    private DrawerLayout drawerLayout = null;
    private ActionBarDrawerToggle dtToggle = null;
    private RecyclerView recyclerView;
    private MainDrawerViewAdapter mainDrawerViewAdapter;
    private MaterialMenuDrawable materialMenu;
    private boolean isDrawerOpened = false;
    private int              actionBarMenuState;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {

            case android.R.id.home:
            {
                drawerLayout.openDrawer(GravityCompat.START);
                Log.i("kkkk","-------------");
                if(!drawerLayout.isDrawerOpen(linearLayout)) {
                    Log.i("kkkk","sdfdsfdsfsdfd");
                    return true;
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }



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
                    case 1://close drawer
                        drawerLayout.closeDrawer(linearLayout);
                        break;
                }
            }
        };


        mainDrawerViewAdapter = new MainDrawerViewAdapter(getApplicationContext(), handler);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        for(int i=0; i<drawerMneu.length; i++){
            mainDrawerViewAdapter.addItem(drawerMneu[i]);
            Log.i("kkkkkk",drawerMneu[i]);
        }


        SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
        String a = pref.getString("user_id", "");
        String b = pref.getString("user_name", "");
        Toast.makeText(getApplicationContext(), a + " : " + b, Toast.LENGTH_SHORT).show();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if(newState == DrawerLayout.STATE_IDLE) {
                    if(isDrawerOpened) {
                        ((MaterialMenuDrawable)(toolbar.getNavigationIcon())).animateIconState(MaterialMenuDrawable.IconState.ARROW);
                        // menu.setIconState(MaterialMenuDrawable.IconState.ARROW);
                    } else {
                        ((MaterialMenuDrawable)(toolbar.getNavigationIcon())).animateIconState(MaterialMenuDrawable.IconState.BURGER);
                        // menu.setIconState(MaterialMenuDrawable.IconState.BURGER);
                    }
                }
            }
        });


        tabLayout = (TabLayout)findViewById(R.id.main_tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("홈"));
        tabLayout.addTab(tabLayout.newTab().setText("지역별"));
        tabLayout.addTab(tabLayout.newTab().setText("음식별"));
//        tabLayout.addTab(tabLayout.newTab().setText("리뷰"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.main_viewPager);

        mainFragment = new MainFragment();
        mainFragment2 = new MainFragment();
        mainFragment3 = new MainFragment();
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