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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by CYSN on 2017-10-21.
 */

public class Main_RankFragment extends Fragment {
    SharedPreferences pref;


    private List<MarketRank_Data> datas = new ArrayList<>();
    private List<MarketRank_Data> list = new ArrayList<>();
    private ListView ranklistview;
    private Main_RankAdapter main_rankAdapter;
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
        bbsRef.addValueEventListener(rankListener);

        main_rankAdapter = new Main_RankAdapter(list, this.getContext());

        ranklistview.setAdapter(main_rankAdapter);
    }

    ValueEventListener rankListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            list.clear();
            datas.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                MarketRank_Data marketRank_data = snapshot.getValue(MarketRank_Data.class); // 컨버팅되서 Bbs로........
                datas.add(marketRank_data);
            }

            MiniComparator comp = new MiniComparator();
            Collections.sort(datas, comp);
            list.add(datas.get(0));
            list.add(datas.get(1));
            list.add(datas.get(2));

            main_rankAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}

class MiniComparator implements Comparator<MarketRank_Data> {
    @Override
    public int compare(MarketRank_Data o1, MarketRank_Data o2) {
        long first = o1.get리뷰수();
        long second = o2.get리뷰수();

        if(first>second){
            return -1;
        } else if (first<second){
            return 1;
        } else {
            return 0;
        }
    }
}