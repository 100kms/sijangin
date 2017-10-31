package org.androidtown.sijang.MarketView;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.polok.routedrawer.RouteDrawer;
import com.github.polok.routedrawer.RouteRest;
import com.github.polok.routedrawer.model.Routes;
import com.github.polok.routedrawer.model.TravelMode;
import com.github.polok.routedrawer.parser.RouteJsonParser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.androidtown.sijang.Data.Review_Data;
import org.androidtown.sijang.Data.Review_Write;
import org.androidtown.sijang.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class MarketMainList extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference  rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");
    DatabaseReference review_allRef = database.getReference().child("즐겨찾기");
    private DatabaseReference bookmarkRef;
    private DatabaseReference bookmark_keyRef;
    private  DatabaseReference bookmarkCheckRef;
    private ListView mainreview = null;
    //private MainReviewList_Adapter mainreview_adapter = null;
    private static final int RC_LOCATION = 1;
    FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private SharedPreferences pref;
    private CarouselView carouselView;
    private long count=0;
    private Map<String, Object> bookmarkValues = new HashMap<>();
    List<Review_Data> review_datas = new ArrayList<>();
    private Boolean isGpsEnabled = false;           //안형우
    private Boolean isNetWorkEnabled = false;       //안형우
    private LocationManager locationManager;        //안형우
    private Button main_mbtn; // 안형우
    public GoogleMap gmap;
    private String place;
    private String marketname;
    private Double latitude;
    private Double longitude;
    private Double mylatitude;
    private Double mylongitude;
    private String user_id, address, content, traffic, user_name;
    private StorageReference marketImageRef, marketImageRef2, marketImageRef1;
    private boolean flag = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketmainlist);

        pref = getSharedPreferences("user_info", MODE_PRIVATE);
        user_id = pref.getString("user_id", "");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //안형우 start
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        main_mbtn = (Button)findViewById(R.id.market_mbtn);              // 안형우
        main_mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpscheck();
                if (isGpsEnabled && isNetWorkEnabled) {
                    getLastLocation();

                }
                //gps 키는 화면으로 가는거
                else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivityForResult(intent, 1234);
                }
            }
        });
        //end

        Intent gIntent = getIntent();
        place = gIntent.getStringExtra("place");
        marketname = gIntent.getStringExtra("market_name");
        latitude = gIntent.getDoubleExtra("위도", 0.0);
        longitude = gIntent.getDoubleExtra("경도", 0.0);
        address = gIntent.getStringExtra("주소");
        content = gIntent.getStringExtra("내용");
        traffic = gIntent.getStringExtra("교통수단");

        database = FirebaseDatabase.getInstance();
        Toolbar toolbar = (Toolbar)findViewById(R.id.marketmain_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("시장 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_back);

        Button write_rv = (Button)findViewById(R.id.marketmainlist_btn_review);
        Button reviewRead = (Button)findViewById(R.id.marketmainlist_btn_read);
        final Button bookmark = (Button)findViewById(R.id.marketmainlist_btn_bookmark);

        final ScrollView scroll = (ScrollView) findViewById(R.id.market_scroll);
        ImageView transparent = (ImageView)findViewById(R.id.imagetrans);

        transparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scroll.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scroll.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scroll.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });

        reviewRead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MarketReviewFragment.class);
                intent.putExtra("marketname", marketname);
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pref.getString("user_id", "").equals("guest")){
                    Toast.makeText(getApplicationContext(), "로그인 후 즐겨찾기 등록이 가능합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    bookmarkValues.put("user_id", user_id);
                    bookmarkValues.put("marketName", marketname);
                    bookmarkValues.put("state", "on");
                    bookmarkValues.put("latitude", latitude);
                    bookmarkValues.put("longitude", longitude);
                    bookmarkValues.put("address", address);
                    bookmarkValues.put("content", content);
                    bookmarkValues.put("traffic", traffic);


                    review_allRef.addValueEventListener(countListener);

                }

            }
        });

        write_rv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(pref.getString("user_id", "").equals("guest")){
                    Toast.makeText(getApplicationContext(), "로그인 후 리뷰등록이 가능합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getApplication(), Review_Write.class);
                    intent.putExtra("marketname", marketname);
                    startActivity(intent);
                }

            }
        });


        TextView marketNameView = (TextView) findViewById(R.id.marketmainlist_text_name);
        TextView trafficView = (TextView) findViewById(R.id.marketmainlist_text_traffic);
        TextView contentView = (TextView) findViewById(R.id.marketmainlist_text_content);
        TextView addressView = (TextView)findViewById(R.id.marketmainlist_text_address);

        getLastLocation();
        marketNameView.setText(marketname);
        trafficView.setText(traffic);
        contentView.setText(content);
        contentView.setMovementMethod(new ScrollingMovementMethod());
        addressView.setText(address);
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.Mymap);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference rootReference = firebaseStorage.getReferenceFromUrl("gs://fir-test-92325.appspot.com");

        marketImageRef = rootReference.child(marketname+"/0.jpg");
        marketImageRef1 = rootReference.child(marketname+"/1.jpg");
        marketImageRef2 = rootReference.child(marketname+"/2.jpg");



        carouselView = (CarouselView) findViewById(R.id.marketmainlist_carousel);
        carouselView.setPageCount(3);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                switch (position){
                    case 0 :
                        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(marketImageRef).into(imageView);
                        break;
                    case 1 :
                        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(marketImageRef1).into(imageView);
                        break;
                    case 2 :
                        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(marketImageRef2).into(imageView);
                        break;
                }
            }
        });

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                System.out.println("one-9");
                gmap = googleMap;

                LatLng latLng = new LatLng(latitude, longitude);

                MarkerOptions marker1 = new MarkerOptions();
                marker1.position(new LatLng(latitude, longitude));
                marker1.title(marketname);


                gmap.addMarker(marker1).showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            }
        });
    }

    ValueEventListener countListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            long child_count = dataSnapshot.getChildrenCount();
            save(child_count);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void save(long child_count){
        if(flag){
            bookmarkRef = database.getReference().child("즐겨찾기").child(Long.toString(child_count));
            bookmarkRef.setValue(bookmarkValues);
            flag=false;
        }

    }


    @SuppressWarnings("MissingPermission")
    @AfterPermissionGranted(RC_LOCATION)
    public void getLastLocation(){
        String[] perms = {android.Manifest.permission.ACCESS_FINE_LOCATION};
        System.out.println("111111");
        if(EasyPermissions.hasPermissions(this, perms)){
            System.out.println("22222");
            mFusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
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
                                MarkerOptions marker1 = new MarkerOptions();
                                marker1.position(new LatLng(latitude, longitude));
                                marker1.title(marketname);

                                MarkerOptions marker2 = new MarkerOptions();
                                marker2.position(new LatLng(mylatitude, mylongitude));
                                marker2.title("현재 내 위치");
                                marker2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                                gmap.addMarker(marker1).showInfoWindow();
                                gmap.addMarker(marker2);


                                //Route(gmap);
                                //gmap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(marketname).snippet("설명")).showInfoWindow();
                                //gmap.addMarker(new MarkerOptions().position(new LatLng(mylatitude, mylongitude)).title("내위치").snippet("설명")).showInfoWindow();
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



    public void Route(GoogleMap gmaps){
        System.out.println("9999999");
        final RouteDrawer routeDrawer = new RouteDrawer.RouteDrawerBuilder(gmaps)
                .withColor(Color.BLUE)
                .withWidth(8)
                .withAlpha(0.3f)
                .withMarkerIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .build();
        System.out.println("000000");
        RouteRest routeRest = new RouteRest();
        System.out.println("111111");
        System.out.println("2나의 위도 : " + mylatitude);
        System.out.println("2나의 경도 : " + mylongitude);
        System.out.println("2위도 : " + latitude);
        System.out.println("2경도 : " + longitude);
        routeRest.getJsonDirections(new LatLng(latitude, longitude), new LatLng(mylatitude, mylongitude), TravelMode.WALKING)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Routes>() {
                    @Override
                    public Routes call(String s) {
                        return new RouteJsonParser<Routes>().parse(s, Routes.class);
                    }
                })
                .subscribe(new Action1<Routes>() {
                    @Override
                    public void call(Routes r) {
                        System.out.println("3경도 : " + longitude);
                        routeDrawer.drawPath(r);
                    }
                });
    }


    public void gpscheck() {
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetWorkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled && isNetWorkEnabled) {
            //mbtn.setBackgroundResource(R.drawable.main_map_gps_on);
            return;
        }
        if (!isGpsEnabled) {
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

    @Override
    protected  void attachBaseContext(Context newBase){
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}