package com.example.csadmin.homework3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Shop extends Fragment {
    ArrayList<String> names;
    String chosenHunterHeal;
    String chosenHunterRest;
    ArrayAdapter<String> THAdapter;
    static TextView purse;
    private HunterStatusListener listener;
    Button healButt;
    Button restButt;
    Button gachaButt;
    private int healCost;
    private int restCost;
    private int gachaCost;
    //need to track gold
        //subtract from the total with each purchase
    //need to implement healing spinner and button
        //choose a hunter and return them to full health
    //need to implement resting spinner and button
        //choose a hunter and return them to full stamina
    //need to implement hunter purchasing Gacha button
        //purchase a new hunter from the underlying list


    public Shop() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        healCost = 100;//cost to heal, in gold
        restCost = 100;//cost to rest, in gold
        gachaCost = 500;//cost to buy new hunter, in gold

        purse = v.findViewById(R.id.gold_count_tally);
        purse.setText("Gold: "+MainActivity.getGoldCount());

        final Spinner healSpinner = v.findViewById(R.id.heal_spinner);
        final Spinner restSpinner = v.findViewById(R.id.rest_spinner);
        THAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_dropdown_item_1line, names);

        healSpinner.setAdapter(THAdapter);
        restSpinner.setAdapter(THAdapter);

        healSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenHunterHeal = healSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        restSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosenHunterRest = restSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        healButt = v.findViewById(R.id.buy_health_button);
        restButt = v.findViewById(R.id.buy_stam_button);
        gachaButt = v.findViewById(R.id.buy_hunter_button);

        healButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(purse.getText().subSequence(6,purse.getText().length()).toString()) >= healCost){
                    healChosenHunter();
                }else{
                    Toast.makeText(getContext(), R.string.insufficientFunds, Toast.LENGTH_SHORT).show();
                }
            }
        });

        restButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(purse.getText().subSequence(6,purse.getText().length()).toString()) >= restCost){
                    restChosenHunter();
                }else{
                    Toast.makeText(getContext(), R.string.insufficientFunds, Toast.LENGTH_SHORT).show();
                }
            }
        });

        gachaButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(purse.getText().subSequence(6,purse.getText().length()).toString()) >= gachaCost){
                    gacha();
                }else{
                    Toast.makeText(getContext(), R.string.insufficientFunds, Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    public static void setGoldTally() {
        purse.setText("Gold: " + MainActivity.getGoldCount());
    }

    public void setNames(ArrayList<Hunter> hunters){
        names = new ArrayList<>();
        for (Hunter h : hunters){
            names.add(h.getName());
        }
    }

    public interface HunterStatusListener{
        void onHealOrRest(String name, int procedure);
        void onGacha();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (HunterStatusListener) context;
    }

    public void healChosenHunter(){
        listener.onHealOrRest(chosenHunterHeal,1);
    }

    public void restChosenHunter(){
        listener.onHealOrRest(chosenHunterRest,0);
    }

    public void gacha(){
        listener.onGacha();
    }

    public void onListUpdate(){
        setNames(MainActivity.getHunters());
        THAdapter.clear();
        THAdapter.addAll(names);
        THAdapter.notifyDataSetChanged();
    }

}
