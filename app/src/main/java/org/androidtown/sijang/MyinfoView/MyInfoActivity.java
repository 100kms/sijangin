package org.androidtown.sijang;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.androidtown.sijang.MyinfoView.MyInfoFavoriteFragment;
import org.androidtown.sijang.MyinfoView.MyInfoReviewFragment;

import java.io.File;

/**
 * Created by CYSN on 2017-10-05.
 */

public class MyInfoActivity extends AppCompatActivity {
    private Button myReviewBtn;
    private Button myFavoriteBtn;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MyInfoReviewFragment myInfoReviewFragment;
    private MyInfoFavoriteFragment myInfoFavoriteFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        myReviewBtn = (Button) findViewById(R.id.myInfo_myReviewView);
        myFavoriteBtn = (Button) findViewById(R.id.myInfo_myFavoriteView);
        viewPager = (ViewPager)findViewById(R.id.myInfo_ViewPager);
        tabLayout = (TabLayout) findViewById(R.id.myInfo_TabLayout);

        myInfoReviewFragment = MyInfoReviewFragment.getInstance();
        myInfoFavoriteFragment = MyInfoFavoriteFragment.getInstance();
        TabLayout.Tab favoriteTab = tabLayout.newTab().setIcon(R.drawable.myinfo_favorite);
        TabLayout.Tab reviewTab = tabLayout.newTab().setIcon(R.drawable.myinfo_review);
        tabLayout.addTab(favoriteTab);
        tabLayout.addTab(reviewTab);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    public class PageAdapter extends FragmentStatePagerAdapter{
        public PageAdapter(FragmentManager manager){
            super(manager);
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return myInfoFavoriteFragment;
                case 1:
                    return myInfoReviewFragment;
                default:
                    return null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



