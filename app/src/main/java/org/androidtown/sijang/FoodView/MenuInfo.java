package org.androidtown.sijang.FoodView;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Latitude;
import org.androidtown.sijang.R;

import java.util.HashMap;
import java.util.Map;

public class MenuInfo extends Activity implements OnMapReadyCallback {

    SharedPreferences pref;

    private Handler stopHandler;
    private Bundle bundle;

    int ki = 0;

    double longtitude;
    double latitude;

    GoogleMap gmap;
    // Write a message to the database
    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    StorageReference rootReference;
    DatabaseReference bbsRef;
    DatabaseReference bookmark_Ref;
    Latitude ltt;
    double temp_longitude;
    double temp_latitude;
    String market_name;
    String store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("one-1");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        System.out.println("one-2");
        //setContentView(R.layout.menu_info);
        System.out.println("one-3");
        //markerOptions3 = new MarkerOptions();
        Intent intent = getIntent();
        String market = intent.getStringExtra("market_key");
        String store = intent.getStringExtra("store_key");


        // 1. 파이어베이스 연결 - DB Connection
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();



        System.out.println("one-4");
        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
        //bbsRef = database.getReference("지도").child("삼선").child("종로곱창");
        bbsRef = database.getReference("지도").child(market).child(store);


        // 3. 레퍼런스 기준으로 데이터베이스에 쿼리를 날리는데, 자동으로 쿼리가 된다.
        //    ( * 파이어 베이스가
        // bbsRef.addValueEventListener(postListener);
        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                    //String key = snapshot.getKey();
                    //System.out.println("asdasndl >> " + snapshot.getValue(Latitude.class).toString());
                    ltt = dataSnapshot.getValue(Latitude.class); // 컨버팅되서 Bbs로........
                    temp_latitude = ltt.getLatitudetitude();
                    temp_longitude = ltt.getLongitude();

                    store_name = dataSnapshot.getRef().getKey().toString();
                    market_name = dataSnapshot.getRef().getParent().getKey().toString() + "시장";


                    // change메서드 안에서 onMapReady를 불러와주는 역할을 넣음
                    // 여기다 넣은것은 이 데이터를 가져오는것이 생명주기를 무시하고, 액티비티 자체가 실행될때 데이터를 가져오기 때문에 데이터를 먼저가져오고 맵을 그리는 순으로 만들기 위해서이다
                    // 이렇게 안하면, 데이터를 먼저 가져오라고 해도 맵을 먼저 그려버리고 그 뒤에 액티비티자체가 실행이 다되고 데이터를 가져오기 때문에
                    Mapchange();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
            }
        });



        stopHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                bundle = msg.getData();
                longtitude = bundle.getDouble("longtitude");
                latitude = bundle.getDouble("latitude");
                System.out.println("캬캬캬 : " + longtitude + "캬캬캬" + latitude);

            }
        };
    }


    public void Mapchange(){

        setContentView(R.layout.menu_info);
        System.out.println("two-1");
        FragmentManager fragmentManager = getFragmentManager();
        System.out.println("two-2");
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        System.out.println("two-3");
        mapFragment.getMapAsync(this);
        System.out.println("two-4");
    }

    public void onMapReady(final GoogleMap map){
        System.out.println("one-9");
        gmap = map;

        double fire_latitude = temp_latitude;
        double fire_longitude = temp_longitude;
        LatLng place = new LatLng(fire_latitude,fire_longitude);
        //LatLng SEOUL = new LatLng(37.56,126.97);
        //LatLng SEOUL2 = new LatLng(a,b);
        //double c = SEOUL2.latitude ;

        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
        //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fire_latitude, fire_longitude), 13));
        gmap.addMarker(new MarkerOptions().position(new LatLng(fire_latitude, fire_longitude)).title(market_name).snippet(store_name)).showInfoWindow();

        /*
        final MarkerOptions markerOptions = new MarkerOptions();
        MarkerOptions marketOptions2 = new MarkerOptions();

        markerOptions.position(SEOUL);
        marketOptions2.position(SEOUL2);

        System.out.println("kkkk : " + a  +  "kkk"  + b);

        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");

        marketOptions2.title("서울3");
        marketOptions2.snippet("한국의 수도3");


        map.addMarker(markerOptions).showInfoWindow();
        map.addMarker(marketOptions2).showInfoWindow();
*/
        /*
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();

                //markerOptions3.icon(Bit);
                //markerOptions3.position(latLng);

               // map.addMarker(markerOptions3);
                System.out.println("레티튜드 : >>>> " + latLng.latitude);
                System.out.println("롱티튜드 : >>>> " + latLng.longitude);

            }

        });
        */

        //map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL2));
        //map.animateCamera(CameraUpdateFactory.zoomTo(17));

        // 맵 스크롤(드래그) 안되게 막는거
        //map.getUiSettings().setScrollGesturesEnabled(false);
        //map.getUiSettings().setAllGesturesEnabled(false);
    }


    //확인 버튼 클릭 , 액티비티 닫기
    public void mOnClose(View v){
        finish();
    }

    public void mfavorite(View v){
        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        String id = pref.getString("user_id", "");
        System.out.println("아이디다 시발 " + id);

        bookmark_Ref = database.getReference("사용자").child(id);
        String market_key = bookmark_Ref.push().getKey();

        Map<String, String> bookmarkValues = new HashMap<>();
        bookmarkValues.put("latitude", Double.toString(temp_latitude));
        bookmarkValues.put("longitude", Double.toString(temp_longitude));
        bookmarkValues.put("시장이름", market_name);

        DatabaseReference market_keyRef = bookmark_Ref.child(market_key);
        market_keyRef.setValue(bookmarkValues);

    }

    //액티비티 이벤트
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return false;
    }

    //백버튼 막기
    public void onBackPressed(){
        return;
    }

}
