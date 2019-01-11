package com.example.csadmin.homework3;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.csadmin.homework3.HunterDetails.adapter;

public class HunterList extends ListFragment{

    ArrayList<String> names;
    ArrayList<Hunter> hunters;
    public HunterList(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                names);
        this.setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    public void setNames(ArrayList<Hunter> hunters) {
        this.hunters = hunters;
        names = new ArrayList<>();
        for (Hunter hunter : hunters) {
            names.add(hunter.getName());
        }
    }
    //implement the onClickListener
    interface listClickListener{
        void itemClicked(String name);
    }
    private listClickListener listener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (listClickListener) context;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //redirect to HunterPopup
        if(listener != null){
            listener.itemClicked(l.getItemAtPosition(position).toString());
        }
    }

    public void onListUpdate(){
        setNames(MainActivity.getHunters());
        adapter.notifyDataSetChanged();
    }
}
