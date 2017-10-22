package org.androidtown.sijang.MainView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.androidtown.sijang.Data.MarketRank_Data;
import org.androidtown.sijang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYSN on 2017-10-21.
 */

public class Main_RankFragment extends Fragment {
    SharedPreferences pref;


    private List<MarketRank_Data> datas = new ArrayList<>();
    private ListView ranklistview;

    // Write a message to the database
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private StorageReference rootReference;
    private DatabaseReference bbsRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main__rank, container, false);
        ranklistview = (ListView) view.findViewById(R.id.fragment_rank_listview);
        // 1. 파이어베이스 연결 - DB Connection

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        bbsRef = database.getReference("시장전체");

        System.out.println("캬캬캬캬");
        bbsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("가나다라마바사 ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MarketRank_Data marketRank_data = snapshot.getValue(MarketRank_Data.class); // 컨버팅되서 Bbs로........
                    System.out.println("시장 이름 : " + marketRank_data.get시장이름());
                    System.out.println("데이터 경도 : " + marketRank_data.get경도());
                    System.out.println("데이터 위도 : " + marketRank_data.get위도());
                    datas.add(marketRank_data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}