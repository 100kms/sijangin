package org.androidtown.sijang;


import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class Fire2Activity extends AppCompatActivity {

    Button btnDel;
    Button btnLoad;
    Button btnData;

    final int REQ_CODE_SELECT_IMAGE = 100;


    // Write a message to the database
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;
    DatabaseReference bbsRef;
    DatabaseReference bbsRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire2);

        // 1. 파이어베이스 연결 - DB Connection
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
        //bbsRef = database.getReference("bbs");

        // 3. 레퍼런스 기준으로 데이터베이스에 쿼리를 날리는데, 자동으로 쿼리가 된다.
        //    ( * 파이어 베이스가
        //bbsRef.addValueEventListener(postListener);

        // 4. 리스트뷰에 목록 세팅


        // 위젯.
        btnDel = (Button) findViewById(R.id.btnDel2);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnData = (Button)findViewById(R.id.btndata);
/*
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String content = editCcontent.getText().toString();

                // 6
                // 6.1 bbs 레퍼런스 (테이블)에 키를 생성한다.
                String key = bbsRef.push().getKey();

                // 6.2 입력될 키, 값 세트 (레코드)를 생성.
                Map<String, String> postValues = new HashMap<>();
                postValues.put("title", title);
                postValues.put("content", content);
                postValues.put("key", key);


                // 6.3 생성된 레코드를 데이터베이스에 입력.

                // 3.1 방식
                //Map<String, Object> keyMap = new HashMap<>();
                //keyMap.put(key, postValues);
                //bbsRef.updateChildren(keyMap);

                // 3.2.1 방식
                DatabaseReference keyRef = bbsRef.child(key);
                keyRef.setValue(postValues);

                // 3.2.2 하위 트리 내용 가져오기
                //DatabaseReference titleRef = bbsRef.child(key).child("title");
                //titleRef.setValue("해결!");
            }
        });
*/
        //갤러리 가서 이미지 추가
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });


        // 파이어베이스에서 이미지 로드
        btnLoad.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");

                StorageReference islandRef = rootReference.child("imageine dragon/ba4");
                StorageReference islandRef2 = rootReference.child("imageine dragon/IMG_20170430_214142911.jpg");


                ImageView img1 = (ImageView)findViewById(R.id.imageload);
                ImageView img2 = (ImageView)findViewById(R.id.imageload2);

                Glide.with(getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef).into(img1);
                Glide.with(getApplicationContext()).using(new FirebaseImageLoader())
                        .load(islandRef2).into(img2);

            }
        });

        btnData.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), FireActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == this.RESULT_OK) {

                rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");


                Uri file = Uri.fromFile(new File(getPath(data.getData())));
                StorageReference riversRef = rootReference.child("imageine dragon/" + "sajin");
                UploadTask uploadTask = riversRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });

            }

        }

    }

    public String getPath(Uri uri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }
}
