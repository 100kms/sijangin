package org.androidtown.sijang.MyinfoView;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.androidtown.sijang.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.myInfo_wellCome_layout);
        myReviewBtn = (Button) findViewById(R.id.myInfo_myReviewBtn);
        myFavoriteBtn = (Button) findViewById(R.id.myInfo_myFavoriteBtn);

        myInfoReviewFragment = MyInfoReviewFragment.getInstance();
        myInfoFavoriteFragment = MyInfoFavoriteFragment.getInstance();
       /* fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.myInfo_frameLayout,myInfoReviewFragment);*/
        relativeLayout.setVisibility(RelativeLayout.GONE);
        getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.myInfo_frameLayout,myInfoReviewFragment).commit();
        //fragmentTransaction.commit();
    }
    public void onClickViewChange(View v){
        if(v.getId() == R.id.myInfo_myFavoriteBtn){
            getSupportFragmentManager().beginTransaction().replace(R.id.myInfo_frameLayout,myInfoFavoriteFragment).commit();
        }
        else if (v.getId() == R.id.myInfo_myReviewBtn){
            getSupportFragmentManager().beginTransaction().replace(R.id.myInfo_frameLayout,myInfoReviewFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        Log.i("kkkkkk","sss");
        super.onBackPressed();
    }
}




