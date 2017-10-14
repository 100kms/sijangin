package org.androidtown.sijang;


import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireActivity extends AppCompatActivity {

    Button btnDel;
    EditText editDel;
    Button btnPost;
    EditText editTitle, editCcontent;

    final int REQ_CODE_SELECT_IMAGE = 100;


    ListView listView;
    ListAdapter adapter;
    List<Bbs> datas = new ArrayList<>();

    // Write a message to the database
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;
    DatabaseReference bbsRef;
    DatabaseReference bbsRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);

        // 1. 파이어베이스 연결 - DB Connection
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
        bbsRef = database.getReference("시장");

        // 3. 레퍼런스 기준으로 데이터베이스에 쿼리를 날리는데, 자동으로 쿼리가 된다.
        //    ( * 파이어 베이스가
        bbsRef.addValueEventListener(postListener);

        // 4. 리스트뷰에 목록 세팅
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListAdapter(datas, this);
        listView.setAdapter(adapter);

        // 위젯.
        btnDel = (Button) findViewById(R.id.btnDel);
        editDel = (EditText) findViewById(R.id.editDel);

        btnPost = (Button) findViewById(R.id.btnPost);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editCcontent = (EditText) findViewById(R.id.editContent);

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

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 삭제
                /*
                bbsRef = database.getReference("bbs").child(editDel.getText().toString());
                bbsRef.removeValue();
                */

                //갤러리 가서 이미지 추가
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

    }

    // 5. 파이어베이스가 호출해주는 이벤트 리스너 콜백
    // ValueEventListener : 경로의 전체 내용에 대한 변경을 읽고 수신 대기합니다.
    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // 위에 선언한 저장소인 datas를 초기화하고
            datas.clear();
            // bbs 레퍼런스의 스냅샷을 가져와서 레퍼런스의 자식노드를 바복문을 통해 하나씩 꺼내서 처리.
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
                Bbs bbs = snapshot.getValue(Bbs.class); // 컨버팅되서 Bbs로........
                bbs.key = key;
                datas.add(bbs);
            }
            Collections.reverse(datas);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
            // ...
        }
    };

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == this.RESULT_OK) {

                rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");

                //File rfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/폴더명");

                Uri file = Uri.fromFile(new File(getPath(data.getData())));
                String temp = file.getLastPathSegment();
                temp = "ba4";

                StorageReference riversRef = rootReference.child("imageine dragon/" + temp);
                //StorageReference riversRef = rootReference.child("imageine dragon/" + file.getLastPathSegment());
                System.out.println("저장된 파일 이름 >>>>>>>>>>> : " + file.getLastPathSegment().toString());
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
