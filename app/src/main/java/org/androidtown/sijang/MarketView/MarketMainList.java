package org.androidtown.sijang.MarketView;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.Review_Data;
import org.androidtown.sijang.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


public class MarketMainList extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference reviewRef;
    private StorageReference rootReference;
    private ListView mainreview = null;
    //private MainReviewList_Adapter mainreview_adapter = null;
    private static final int RC_LOCATION = 1;
    FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    List<Review_Data> review_datas = new ArrayList<>();
    public GoogleMap gmap;
    private String place;
    private String marketname;
    private Double latitude;
    private Double longitude;
    private Double mylatitude;
    private Double mylongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketmainlist);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent gIntent = getIntent();
        place = gIntent.getStringExtra("place");
        marketname = gIntent.getStringExtra("market_name");
        latitude = gIntent.getDoubleExtra("위도", 0.0);
        longitude = gIntent.getDoubleExtra("경도", 0.0);
        String address = gIntent.getStringExtra("주소");
        String content = gIntent.getStringExtra("내용");

        TextView marketName = (TextView) findViewById(R.id.marketmainlist_text_name);
        getLastLocation();
        marketName.setText(marketname);
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.Mymap);


        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                System.out.println("one-9");
                gmap = googleMap;
                //getLastLocation();

                //double fire_latitude = latitude;
                //double fire_longitude = longitude;

                LatLng latLng = new LatLng(latitude, longitude);
                //LatLng SEOUL = new LatLng(37.56,126.97);
                //LatLng SEOUL2 = new LatLng(a,b);
                //double c = SEOUL2.latitude ;

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
                //gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fire_latitude, fire_longitude), 13));
                //googleMap.addMarker(new MarkerOptions().position(new LatLng(fire_latitude, fire_longitude)).title(marketname).snippet("설명")).showInfoWindow();
                System.out.println("나의 위도 : " + mylatitude);
                System.out.println("나의 경도 : " + mylongitude);

                //googleMap.addMarker(new MarkerOptions().position(new LatLng(mylatitude, mylongitude)).title("사과")).showInfoWindow();

            }
        });
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
                                marker1.snippet("설명");

                                MarkerOptions marker2 = new MarkerOptions();
                                marker2.position(new LatLng(mylatitude, mylongitude));
                                marker2.title("내 위치");
                                marker2.snippet("설명");

                                gmap.addMarker(marker1).showInfoWindow();
                                gmap.addMarker(marker2).showInfoWindow();

                                Route(gmap);
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

}
      /*  database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        reviewRef = database.getReference("리뷰").child("시장별").child(market_name);
        reviewRef.addValueEventListener(reviewListener);

        mainreview = (ListView) findViewById(R.id.marketmainlist_listview_review);
       // mainreview_adapter = new Market(review_datas, this);

        mainreview.setAdapter(mainreview_adapter);




        Button write = (Button)findViewById(R.id.marketmainlist_btn_review);

        write.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketMainList.this, Review_Write.class);
                startActivity(intent);
            }
        });


    }
    ValueEventListener reviewListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            review_datas.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Review_Data review_data = snapshot.getValue(Review_Data.class);
                review_datas.add(review_data);
            }
            Collections.reverse(review_datas);
            mainreview_adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public static void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            return;
        }

        int totalHeight = 0;
        for(int i = 0; i<listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}*/