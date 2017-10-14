package org.androidtown.sijang;

import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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




