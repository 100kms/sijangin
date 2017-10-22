package org.androidtown.sijang.MainView;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Latitude;
import org.androidtown.sijang.Data.Market_Data;
import org.androidtown.sijang.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by CYSN on 2017-10-21.
 */

public class MainMapFragment extends Fragment implements OnMapReadyCallback {

    private Handler handler ;
    public MainMapFragment(){

    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    SharedPreferences pref;
    protected Location mLastLocation;
    private static final int RC_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;

    private Handler stopHandler;
    private Bundle bundle;

    private double pita;
    private double temp;
    private double calcnum;
    private int realnum;
    private double mylatitude;
    private double mylongitude;

    private int index =0;

    private List<Market_Data> datas = new ArrayList<>();
    String[] names = {"강서", "강송", "강양구", "도노강", "동관금영", "성종용중", "은서마", "중광동성"};


    private GoogleMap gmap;
    // Write a message to the database
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private StorageReference rootReference;
    private DatabaseReference bbsRef;
    private DatabaseReference bookmark_Ref;
    private Button favoritebtn;
    private Latitude ltt;
    private double temp_longitude;
    private double temp_latitude;
    private String market_name;
    private String store_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_map, container, false);


        favoritebtn = (Button)view.findViewById(R.id.favorite_btn);

        // 1. 파이어베이스 연결 - DB Connection

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        getLastLocation();

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        System.out.println("one-4");
        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
        //bbsRef = database.getReference("지도").child("삼선").child("종로곱창");
        for(int i=0; i<names.length; i++) {
                //bbsRef = database.getReference("시장").child("강서");
            bbsRef = database.getReference("시장").child(names[i]);
            //System.out.println("숫자임 : " + i);

            // 3. 레퍼런스 기준으로 데이터베이스에 쿼리를 날리는데, 자동으로 쿼리가 된다.
            //    ( * 파이어 베이스가
            // bbsRef.addValueEventListener(postListener);
            bbsRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("가나다라마바사 ");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Market_Data market_data = snapshot.getValue(Market_Data.class); // 컨버팅되서 Bbs로........
                        System.out.println("시장 이름 : " + market_data.get시장이름());
                        System.out.println("데이터 경도 : " + market_data.get경도());
                        System.out.println("데이터 위도 : " + market_data.get위도());
                        datas.add(market_data);
                    }

                    index++;
                    if(index== names.length){
                        Sort();
                        index=0;
                        System.out.println("데이터 사이즈 : " + datas.size());
                        System.out.println("이름길이 사이즈 : " + names.length);
                    }
                    //String key = snapshot.getKey();
                    //System.out.println("asdasndl >> " + snapshot.getValue(Latitude.class).toString());

                    // change메서드 안에서 onMapReady를 불러와주는 역할을 넣음
                    // 여기다 넣은것은 이 데이터를 가져오는것이 생명주기를 무시하고, 액티비티 자체가 실행될때 데이터를 가져오기 때문에 데이터를 먼저가져오고 맵을 그리는 순으로 만들기 위해서이다
                    // 이렇게 안하면, 데이터를 먼저 가져오라고 해도 맵을 먼저 그려버리고 그 뒤에 액티비티자체가 실행이 다되고 데이터를 가져오기 때문에


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }
        //Sort();
        Mapchange();

    }

    public void Sort(){
        System.out.println("Sort들어옴");

            temp = 5000;
            System.out.println("데이터 사이즈 :" + datas.size());
            for (int j = 0; j < datas.size(); j++) {
                pita = Math.pow((mylatitude - datas.get(j).get경도()),2) + Math.pow((mylongitude - datas.get(j).get위도()),2);
                calcnum = Math.sqrt(pita);
                System.out.println("calcnum : " + calcnum);
                if (calcnum < temp) {
                    temp = calcnum;
                    realnum = j;
                }
            }
            System.out.println("정렬 끝 : " + realnum);
            System.out.println("직전 realnum : " + realnum);
            LatLng place = new LatLng(datas.get(realnum).get위도(), datas.get(realnum).get경도());
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
            gmap.addMarker(new MarkerOptions().position(new LatLng(datas.get(realnum).get위도(), datas.get(realnum).get경도())).title("내위치").snippet("설명")).showInfoWindow();


    }

    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    public void getLastLocation(){
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION};
        System.out.println("111111");
        if(EasyPermissions.hasPermissions(getApplicationContext(), perms)){
            System.out.println("22222");
            mFusedLocationClient.getLastLocation().addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override

                public void onComplete(@NonNull Task<Location> task) {
                    System.out.println("333333");
                    if(task.isSuccessful() && task.getResult() != null){
                        System.out.println("444444");
                        mLastLocation = task.getResult();
                        try {
                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.KOREA);
                            List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);

                            if (addresses.size() >0) {
                                Address address = addresses.get(0);
                                mylatitude = address.getLatitude();
                                mylongitude = address.getLongitude();

                                System.out.println("1나의 위도 : " + mylatitude);
                                System.out.println("1나의 경도 : " + mylongitude);


                                //MarkerOptions marker1 = new MarkerOptions();

                                //gmap.addMarker(marker1).showInfoWindow();
                                //gmap.addMarker(marker2).showInfoWindow();
                                //System.out.println("직전 realnum : " + realnum);
                                //LatLng place = new LatLng(datas.get(realnum).get경도(), datas.get(realnum).get위도());
                                //LatLng SEOUL = new LatLng(37.56,126.97);
                                //LatLng SEOUL2 = new LatLng(a,b);
                                //double c = SEOUL2.latitude ;
                                //System.out.println("직전 realnum : " + realnum);
                               // LatLng place = new LatLng(datas.get(realnum).get경도(), datas.get(realnum).get위도());
                                //gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
                                //gmap.addMarker(new MarkerOptions().position(new LatLng(datas.get(realnum).get경도(), datas.get(realnum).get위도())).title("내위치").snippet("설명")).showInfoWindow();
                                //gmap.addMarker(new MarkerOptions().position(new LatLng(datas.get(realnum).get경도(), datas.get(realnum).get위도())).title("내위치").snippet("설명")).showInfoWindow();
                                //contacts.add(new Contact(address.getLongitude(), address.getLatitude(),address.getCountryName() + " " +address.getLocality()+ " " + address.getFeatureName()));
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else{
            EasyPermissions.requestPermissions(this, "This app needs access to your location to know where you are", RC_LOCATION, perms);
        }
    }



    public void onMapReady(final GoogleMap map) {
        System.out.println("one-9");
        gmap = map;

        double fire_latitude = temp_latitude;
        double fire_longitude = temp_longitude;
        LatLng place = new LatLng(fire_latitude, fire_longitude);
        //LatLng SEOUL = new LatLng(37.56,126.97);
        //LatLng SEOUL2 = new LatLng(a,b);
        //double c = SEOUL2.latitude ;

        //gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 16));
        //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fire_latitude, fire_longitude), 13));
        //gmap.addMarker(new MarkerOptions().position(new LatLng(fire_latitude, fire_longitude)).title(market_name).snippet(store_name)).showInfoWindow();
    }


    public void Mapchange(){

        System.out.println("two-1");
        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager()
        System.out.println("two-2");
        MapFragment mapFragment = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.Mainmap);
        System.out.println("two-3");
        mapFragment.getMapAsync(this);
        System.out.println("two-4");
    }

}