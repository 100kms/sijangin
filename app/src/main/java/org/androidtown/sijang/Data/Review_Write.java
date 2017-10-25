package org.androidtown.sijang.Data;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.androidtown.sijang.MainView.MainActivity;
import org.androidtown.sijang.R;
import org.androidtown.sijang.Util.ImageUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LG on 2017-10-05.
 */

public class Review_Write extends AppCompatActivity {
    static private String count = "0";
    private EditText e_title;
    private EditText e_content;
    private Button create;
    private Button add;
    private Button del;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private RatingBar ratingBar;
    private String imagePath;
    private Uri imageUri1, imageUri2, imageUri3;
    private boolean flag = true;

    private String id;
    private String name;
    private String marketname;
    private int add_count = 0;
    private double star;
    private String getTime;
    private String img_count;
    private long child_count = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference  rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
    DatabaseReference review_allRef = database.getReference().child("review").child("전체");
    Map<String, Object> reviewValues = new HashMap<>();
    DatabaseReference reviewRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        System.out.println("============== add_count : " + add_count);

        SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
        id = pref.getString("user_id", "");
        name = pref.getString("user_name", "");

        Intent gIntent = getIntent();
        marketname = gIntent.getStringExtra("marketname");

        e_title = (EditText)findViewById(R.id.write_edit_title);
        e_content = (EditText)findViewById(R.id.write_edit_content);

        create = (Button)findViewById(R.id.write_btn_ok);
        add = (Button)findViewById(R.id.write_btn_add);
        del = (Button)findViewById(R.id.write_btn_del);

        image1 = (ImageView)findViewById(R.id.write_image_one);
        image2 = (ImageView)findViewById(R.id.write_image_two);
        image3 = (ImageView)findViewById(R.id.write_image_three);

        ratingBar = (RatingBar)findViewById(R.id.write_ratingbar);

        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        image3.setVisibility(View.GONE);

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                add_count++;
                if(add_count > 3){
                    add_count = 3;
                    Toast.makeText(getApplicationContext(), "더이상 추가 하실 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                add_imageview(add_count);
            }
        });

        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                add_count--;
                if(add_count < 0){
                    add_count = 0;
                    Toast.makeText(getApplicationContext(), "먼저 추가 사진칸을 추가해 주십시오", Toast.LENGTH_SHORT).show();
                }
                add_imageview(add_count);
            }
        });

        image1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 101);
            }
        });

        image2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 102);
            }
        });

        image3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 103);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
                star = rating;
            }
        });

        reviewRef = database.getReference("시장전체").child(marketname);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);

        create.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title = e_title.getText().toString();
                String content = e_content.getText().toString();

                reviewValues.put("title", title);
                reviewValues.put("content", content);
                reviewValues.put("user_id", id);
                reviewValues.put("name", name);
                reviewValues.put("marketname", marketname);
                reviewValues.put("star", star);
                reviewValues.put("date", getTime);


                review_allRef.addValueEventListener(countListener);

                onStarClicked(reviewRef);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    ValueEventListener countListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            long child_count = dataSnapshot.getChildrenCount();
            send(child_count);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void send(long count){
        System.out.println(count);
        if(flag){
            switch (add_count){
                case 1 :
                    if(imageUri1!=null){
                        img_count="1";
                        StorageReference riversRef1 = rootReference.child(String.valueOf(count)+"/1");
                        UploadTask uploadTask1 = riversRef1.putFile(imageUri1);

                        // Register observers to listen for when the download is done or if it fails
                        uploadTask1.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "1-1에러" + imageUri1, Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "1-2에러" + imageUri1, Toast.LENGTH_SHORT).show();
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        });
                    }
                    break;
                case 2 :
                    if(imageUri2!=null) {
                        img_count="2";
                        StorageReference riversRef1 = rootReference.child(String.valueOf(count)+"/1");
                        UploadTask uploadTask1 = riversRef1.putFile(imageUri1);
                        StorageReference riversRef2 = rootReference.child(String.valueOf(count)+"/2");
                        UploadTask uploadTask2 = riversRef2.putFile(imageUri2);

                        uploadTask1.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "1-1에러" + imageUri1, Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "1-2에러" + imageUri1, Toast.LENGTH_SHORT).show();
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        });
                        // Register observers to listen for when the download is done or if it fails
                        uploadTask2.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "2-1에러" + imageUri2, Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "2-2에러" + imageUri2, Toast.LENGTH_SHORT).show();
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        });
                    }
                    break;
                case 3 :
                    if(imageUri3!=null){
                        img_count="3";
                        StorageReference riversRef1 = rootReference.child(String.valueOf(count)+"/1");
                        UploadTask uploadTask1 = riversRef1.putFile(imageUri1);
                        StorageReference riversRef2 = rootReference.child(String.valueOf(count)+"/2");
                        UploadTask uploadTask2 = riversRef2.putFile(imageUri2);
                        StorageReference riversRef3 = rootReference.child(String.valueOf(count)+"/3");
                        UploadTask uploadTask3 = riversRef3.putFile(imageUri3);

                        uploadTask1.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "1-1에러" + imageUri1, Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "1-2에러" + imageUri1, Toast.LENGTH_SHORT).show();
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        });
                        // Register observers to listen for when the download is done or if it fails
                        uploadTask2.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "2-1에러" + imageUri2, Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "2-2에러" + imageUri2, Toast.LENGTH_SHORT).show();
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        });
                        // Register observers to listen for when the download is done or if it fails
                        uploadTask3.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getApplicationContext(), "3-1에러" + imageUri3, Toast.LENGTH_SHORT).show();
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "3-2에러" + imageUri3, Toast.LENGTH_SHORT).show();
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            }
                        });
                    }
                    break;
            }
            reviewValues.put("img_count", img_count);
            DatabaseReference review_allRef2 = review_allRef.child(String.valueOf(count));
            review_allRef2.setValue(reviewValues);
            flag = false;
        }
    }

    public void add_imageview(int add_count){

//        image1 = (ImageView)findViewById(R.id.write_image_one);
//        image2 = (ImageView)findViewById(R.id.write_image_two);
//        image3 = (ImageView)findViewById(R.id.write_image_three);

        switch (add_count){
            case 0 :
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                break;
            case 1 :
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.GONE);
                image3.setVisibility(View.GONE);
                break;
            case 2 :
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.GONE);
                break;
            case 3 :
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.VISIBLE);
                image3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode > 100)
        {
            if(resultCode==RESULT_OK) {
                Uri imageUri = data.getData();
                imagePath = getPath(imageUri);
                if(requestCode==101) {
                    updateImageView(image1);
                    imageUri1 = Uri.fromFile(new File(getPath(data.getData())));
                }else if(requestCode==102) {
                    updateImageView(image2);
                    imageUri2 = Uri.fromFile(new File(getPath(data.getData())));
                }else if(requestCode==103){
                    updateImageView(image3);
                    imageUri3 = Uri.fromFile(new File(getPath(data.getData())));}
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        CursorLoader cursorLoader = new CursorLoader(this,uri,projection,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    private void updateImageView(ImageView iv) {
        int degree = ImageUtil.GetExifOrientation(imagePath);
        Bitmap resizeBitmap = ImageUtil.loadBackgroundBitmap(
                Review_Write.this, imagePath);
        Bitmap rotateBitmap = ImageUtil.GetRotatedBitmap(resizeBitmap, degree);
        Bitmap roundBitmap = ImageUtil.getRoundedCornerBitmap(rotateBitmap);
        iv.setImageBitmap(roundBitmap);
        resizeBitmap.recycle();
    }

    private void onStarClicked(DatabaseReference reviewRef) {
        reviewRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                MarketRank_Data marketRank_data = mutableData.getValue(MarketRank_Data.class);
                if (marketRank_data == null) {
                    return Transaction.success(mutableData);
                }

                marketRank_data.set리뷰수(marketRank_data.get리뷰수()+1);

                mutableData.setValue(marketRank_data);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
            }
        });
    }

}
/* 파이어베이스에 데이터 집어넣는 클래스

package org.androidtown.sijang;

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

import java.util.HashMap;
import java.util.Map;

public class Review_Write extends AppCompatActivity {
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
        cancel = (Button)findViewById(R.id.write_btn_cancel);
        add = (Button)findViewById(R.id.write_btn_add);
        del = (Button)findViewById(R.id.write_btn_del);



        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
        review_marketRef = database.getReference("시장").child("강송").child("2시장");
        //review_userRef = database.getReference("리뷰").child("개인별").child("변수아이디");
        //review_allRef = database.getReference("리뷰").child("전체");


        create.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String title = e_title.getText().toString();
                String content = e_content.getText().toString();

                String market_key = review_marketRef.push().getKey();

                Map<String, String> reviewValues = new HashMap<>();
                reviewValues.put("경도", "123");
                reviewValues.put("위도", "123");
                reviewValues.put("교통수단", "버스");
                reviewValues.put("내용", "내용입니다");
                reviewValues.put("사진1", "a");
                reviewValues.put("사진2", "b");
                reviewValues.put("시장이름", "asd");



                DatabaseReference market_keyRef = review_marketRef.child(market_key);
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


*/