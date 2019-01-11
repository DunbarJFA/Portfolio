package com.example.csadmin.homework3;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HunterPopup extends AppCompatActivity {
    Hunter hunter;
    ArrayList<Treasure> treasures;
    String portrait;
    TreasureDetailAdapter adapter;
    RecyclerView hunterPopupRecycler;
    Button sellAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunter_popup);

        //setup toolbar with up button
        Toolbar toolbar = findViewById(R.id.toolbar_popup);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //get hunter details and array of treasures
        hunter = (Hunter) getIntent().getExtras().get("Hunter");
        if(hunter != null) {
            //set treasures
            treasures = hunter.getTreasure();
            //set portrait
            portrait = hunter.getPortraitID();
        }
        //get reference to the imageview
        ImageView popupPortrait = findViewById(R.id.PopupPortrait);
        popupPortrait.setImageResource(popupPortrait.getContext().getResources().getIdentifier(portrait,"drawable","com.example.csadmin.homework3"));

        //set all hunter stats
        TextView name = findViewById(R.id.PopupName);
        name.setText(hunter.getName());
        TextView health = findViewById(R.id.PopupHealth);
        health.setText("Health: " + hunter.getHealth() + "/" + hunter.getTotalHealth());
        TextView stamina = findViewById(R.id.PopupStamina);
        stamina.setText("Stamina: " + hunter.getStamina() + "/" + hunter.getTotalStamina());
        TextView luck = findViewById(R.id.PopupLuck);
        luck.setText("Luck: "+hunter.getLuck());
        TextView specialty = findViewById(R.id.PopupSpecialty);
        specialty.setText("Specialty: "+hunter.getSpecialty());

        //set up Sell button
        sellAll = findViewById(R.id.sell_all);
        sellAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hunter.sellTreasure();
                adapter.notifyItemRangeRemoved(0,3);
                HunterDetails.sellTreasure(hunter);
                HunterDetails.adapter.notifyItemRangeRemoved(0,3);
                Shop.setGoldTally();
            }
        });

        //get reference to recyclerview
        hunterPopupRecycler = findViewById(R.id.PopupRecyclerView);
        //create instance of custom RecyclerViewAdapter
        adapter = new TreasureDetailAdapter(treasures);
        hunterPopupRecycler.setAdapter(adapter);

        //create a layout manager and attach it
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        hunterPopupRecycler.setLayoutManager(layoutManager);

    }
}


