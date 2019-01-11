package com.example.csadmin.homework3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HunterDetails extends Fragment {
    static ArrayList<Hunter> hunters;
    static HunterDetailAdapter adapter;

    public HunterDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get reference to RecyclerView
        final RecyclerView hunterDetailRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_hunter_details, container, false);

        //get array of Hunters
        hunters = MainActivity.getHunters();

        //create instance of the custom RecyclerViewAdaptor
        adapter = new HunterDetailAdapter(hunters);
        hunterDetailRecycler.setAdapter(adapter);

        //create a layout manager and attach it
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        hunterDetailRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new HunterDetailAdapter.Listener() {
            @Override
            public void onClick(Hunter hunter) {
                Intent intent = new Intent(getActivity(), HunterPopup.class);
                intent.putExtra("Hunter",hunter);
                startActivity(intent);
            }
        });

        return hunterDetailRecycler;
    }

    public static void sellTreasure(Hunter hunter){
        for (Hunter h : hunters){
            if (h.getName().equals(hunter.getName())){
                h.sellTreasure();
            }
        }
    }
    //pass clicked Hunter object to HunterPopup
}
