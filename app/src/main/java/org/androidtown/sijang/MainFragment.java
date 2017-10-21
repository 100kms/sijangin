package org.androidtown.sijang;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.labo.kaji.fragmentanimations.MoveAnimation;

/**
 * Created by CYSN on 2017-10-21.
 */

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_main_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(mainFragmentAdapter);
        mainFragmentAdapter.addItem(R.drawable.bun1);
        mainFragmentAdapter.addItem(R.drawable.bun1);
        mainFragmentAdapter.addItem(R.drawable.bun1);
        mainFragmentAdapter.addItem(R.drawable.bun1);
        mainFragmentAdapter.addItem(R.drawable.bun1);
        mainFragmentAdapter.addItem(R.drawable.bun1);
    }
}
