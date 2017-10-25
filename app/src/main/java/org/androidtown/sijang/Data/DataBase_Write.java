
package org.androidtown.sijang.Data;

import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.R;

import java.util.HashMap;
import java.util.Map;

public class DataBase_Write extends AppCompatActivity {
    static private String count = "0";
    private EditText e_title;
    private EditText e_content;
    private Button create;
    private Button cancel;
    private Button add;
    private Button del;



    private String id;
    private String name;
    private int add_count = 0;

    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;
    DatabaseReference review_marketRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        System.out.println("============== add_count : " + add_count);

        SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
        id = pref.getString("user_id", "");
        name = pref.getString("user_name", "");

        e_title = (EditText)findViewById(R.id.write_edit_title);
        e_content = (EditText)findViewById(R.id.write_edit_content);

        create = (Button)findViewById(R.id.write_btn_ok);
        add = (Button)findViewById(R.id.write_btn_add);
        del = (Button)findViewById(R.id.write_btn_del);



        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
        //review_marketRef = database.getReference("시장").child("중광동성").child("청량리시장");

        //review_userRef = database.getReference("리뷰").child("개인별").child("변수아이디");
        //review_allRef = database.getReference("리뷰").child("전체");


        create.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title2 = e_title.getText().toString();
                String content2 = e_content.getText().toString();
                //review_marketRef = database.getReference("지도").child(title2).child(content2);
                review_marketRef = database.getReference("review").child(title2).child(content2);
                //String market_key = review_marketRef.push().getKey();

                Map<String, String> reviewValues = new HashMap<>();
                reviewValues.put("content", "내용");
                reviewValues.put("data_record", "2017-10-01");
                reviewValues.put("image", "-KwPJueK9Yn2Hs6TJffg/");
                reviewValues.put("image_count", "3");
                reviewValues.put("market_text", "가시장");
                reviewValues.put("replace_text", "나음식");
                reviewValues.put("star", "3.5");
                reviewValues.put("user_id", "keealsgu");










                DatabaseReference market_keyRef = review_marketRef;
                market_keyRef.setValue(reviewValues);


                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
            }
        });

    }


    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader cursorLoader = new CursorLoader(this,uri,projection,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

}

