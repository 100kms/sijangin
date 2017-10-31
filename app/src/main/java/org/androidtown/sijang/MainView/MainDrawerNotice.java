package org.androidtown.sijang.MainView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.androidtown.sijang.R;

import java.util.ArrayList;

/**
 * Created by CYSN on 2017-10-31.
 */

public class MainDrawerNotice extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_notice);
        this.setFinishOnTouchOutside(false);

        arrayList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.notice_Listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_main_notice_item,R.id.main_notice_textView);

        arrayAdapter.add("디자인 UI가 최신화되었습니다. - 2017.10.30");
        arrayAdapter.add("어플리케이션 편의성 개선 - 2017.10.28");
        arrayAdapter.add("카카오톡 로그인기능 추가 - 2017.10.25");
        listView.setAdapter(arrayAdapter);
        listView.setDividerHeight(1);

    }
    @Override
    protected  void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}

