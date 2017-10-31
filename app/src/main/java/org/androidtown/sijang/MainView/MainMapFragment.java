package org.androidtown.sijang.MainView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.sijang.Data.Market_Data;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by CYSN on 2017-10-21.
 */

public class MainMapFragment extends Fragment implements OnMapReadyCallback {
    protected Location mLastLocation;
    private static final int RC_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;


    private double pita;
    private double temp;
    private double calcnum;
    private int realnum;
    private double mylatitude = -1;
    private double mylongitude = -1;

    MarkerOptions default_marker;

    private int index = 0;

    private List<Market_Data> datas = new ArrayList<>();
    String[] names = {"강서", "강송", "강양구", "도노강", "동관금영", "성종용중", "은서마", "중광동성"};

    private GoogleMap gmap;
    private FirebaseDatabase database;
    private DatabaseReference bbsRef;

    private Button mbtn;
    private Boolean isGpsEnabled = false;
    private Boolean isNetWorkEnabled = false;
    private LocationManager locationManager;
    View view;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_map, container, false);


        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mbtn = (Button) view.findViewById(R.id.mbtn);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());
        database = FirebaseDatabase.getInstance();
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpscheck();
                if (isGpsEnabled && isNetWorkEnabled) {
                    getLastLocation();

                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(intent, 1234);
                }
            }
        });
        Mapchange();

        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetWorkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled && isNetWorkEnabled) {
            //mbtn.setText("on");
            mbtn.setBackgroundResource(R.drawable.gps_on);
            getLastLocation();
            if (gmap != null && mylatitude != -1 && mylatitude != -1) {
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mylatitude, mylongitude), 13));
            }
        }
        if (!isGpsEnabled) {
            //mbtn.setText("off");
            mbtn.setBackgroundResource(R.drawable.gps_off);
        }
        if (!isNetWorkEnabled) {

        }

        System.out.println("one-4");

        for (int i = 0; i < names.length; i++) {
            bbsRef = database.getReference("시장").child(names[i]);
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
                    if (index == names.length) {
                        //Sort();
                        getLastLocation();
                        index = 0;
                        System.out.println("데이터 사이즈 : " + datas.size());
                        System.out.println("이름길이 사이즈 : " + names.length);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException());
                }
            });
        }

        //Sort();

    }

    public void Sort() {
        System.out.println("Sort들어옴");

        temp = 5000;
        System.out.println("데이터 사이즈 :" + datas.size());

        for (int j = 0; j < datas.size(); j++) {
            gmap.addMarker(new MarkerOptions().position(new LatLng(datas.get(j).get위도(), datas.get(j).get경도())).title(datas.get(j).get시장이름()).snippet(datas.get(j).get주소()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).alpha(0.7f));
            pita = Math.pow((mylatitude - datas.get(j).get위도()), 2) + Math.pow((mylongitude - datas.get(j).get경도()), 2);
            calcnum = Math.sqrt(pita);
            System.out.println("calcnum : " + calcnum);
            if (calcnum < temp) {
                temp = calcnum;
                realnum = j;
            }
        }
        System.out.println("정렬 끝 : " + realnum);
        System.out.println("직전 realnum : " + realnum);
        System.out.println("내위치 경도: " + mylongitude);
        System.out.println("내위치 위도 : " + mylatitude);

        //GPS켜지면 디폴트 마커 삭제
        default_marker.position(new LatLng(37, 127)).title("날 어떻게 찾았지?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).alpha(1f);
        default_marker.visible(false);
        gmap.addMarker(default_marker);

        LatLng place = new LatLng(datas.get(realnum).get위도(), datas.get(realnum).get경도());
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));
        gmap.addMarker(new MarkerOptions().position(new LatLng(datas.get(realnum).get위도(), datas.get(realnum).get경도())).title(datas.get(realnum).get시장이름()).snippet(datas.get(realnum).get주소()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).alpha(0.7f)).showInfoWindow();
        //gmap.addMarker(new MarkerOptions().position(new LatLng(mylatitude, mylongitude)).title("현재 내 위치").snippet("　　▼　　").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).alpha(0.7f)).showInfoWindow();


        // 원 그리기
        onCircle(datas.get(realnum).get위도(), datas.get(realnum).get경도());
        gmap.addMarker(new MarkerOptions().position(new LatLng(mylatitude, mylongitude)).title("현재 내 위치").snippet("　　▼　　").icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location2))).showInfoWindow();
        //mbtn.setBackgroundResource(R.drawable.main_map_gps_on);
        mbtn.setBackgroundResource(R.drawable.gps_on);


    }

    // 내 위치 찾기
    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    public void getLastLocation() {
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION};
        System.out.println("111111");
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            System.out.println("22222");
            mFusedLocationClient.getLastLocation().addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    System.out.println("333333");
                    System.out.println("task success : " + task.isSuccessful());
                    System.out.println("task getResult : " + task.getResult());
                    if (task.isSuccessful() && task.getResult() != null) {
                        System.out.println("444444");
                        mLastLocation = task.getResult();
                        try {
                            System.out.println("1나의 위도1 : " + mLastLocation.getLatitude());
                            System.out.println("1나의 위도1 : " + mLastLocation.getLongitude());
                            mylatitude = mLastLocation.getLatitude();
                            mylongitude = mLastLocation.getLongitude();
                            Sort();
                        } catch (Exception e) {
                            System.out.println("오류뜸");
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your location to know where you are", RC_LOCATION, perms);
        }

    }


    public void onMapReady(final GoogleMap map) {
        gmap = map;

        default_marker = new MarkerOptions();
        default_marker.position(new LatLng(37.603227, 127.024964)).title("GPS를 켜주세요").snippet("왼쪽 위 버튼").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).alpha(0.8f);
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.603227, 127.024964), 15));
        gmap.addMarker(default_marker).showInfoWindow();

    }


    public void Mapchange() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.Mainmap);
        System.out.println("two-3");
        mapFragment.getMapAsync(this);
        System.out.println("two-4");
    }

    public void gpscheck() {
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetWorkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled && isNetWorkEnabled) {
            //mbtn.setBackgroundResource(R.drawable.main_map_gps_on);
            return;
        }
        if (!isGpsEnabled) {
            //mbtn.setBackgroundResource(R.drawable.main_map_gps_off);
            mbtn.setBackgroundResource(R.drawable.gps_off);
            Toast.makeText(getApplicationContext(), "GPS가 꺼져있습니다.", Toast.LENGTH_SHORT).show();
        }
        if (!isNetWorkEnabled) {
            //Toast.makeText(getApplicationContext(), "Network가 꺼져있습니다.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("결과 : " + requestCode + "   " + resultCode);
        if (requestCode == 1234 && resultCode == 0) {
            System.out.println("리퀘스트 들어옴");
            //mbtn.setBackgroundResource(R.drawable.main_map_gps_loading);
            //mbtn.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "위치버튼을 다시 눌러주십시오", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "위치버튼을 다시 눌러주십시오", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "위치버튼을 다시 눌러주십시오", Toast.LENGTH_LONG).show();
            gpscheck();
        }
    }

    public void onCircle(double mlatitude, double mlogitude){
        CircleOptions circleOptions = new CircleOptions().center(new LatLng(mlatitude, mlogitude)).radius(200).strokeWidth(2f).strokeColor(Color.parseColor("#993823")).fillColor(Color.parseColor("#77FF9696"));
        this.gmap.addCircle(circleOptions);
    }
}