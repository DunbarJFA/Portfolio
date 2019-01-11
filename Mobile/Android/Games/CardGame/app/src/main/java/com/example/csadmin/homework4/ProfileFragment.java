package com.example.csadmin.homework4;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ProfileFragment extends android.support.v4.app.Fragment {
    Button buttonPackOpen;
    int coinsCount;
    TextView coinNumber;
    int packCount;
    TextView packNumber;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    FragmentManager fragMan;
    PackOpenFragment packOpen;
    TextView cardName;
    TextView cardRarity;
    TextView cardAtk;
    TextView cardHp;
    TextView flavor1;
    TextView flavor2;
    TextView flavor3;
    int cardCount;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DrawerLayout dl = findViewById(R.id.Drawer_Layout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        cardName = view.findViewById(R.id.card_Title);
        cardRarity = view.findViewById(R.id.card_Rarity);
        cardAtk = view.findViewById(R.id.card_Atk);
        cardHp = view.findViewById(R.id.card_HP);
        flavor1 = view.findViewById(R.id.card_Flavor1);
        flavor2 = view.findViewById(R.id.card_Flavor2);
        flavor3 = view.findViewById(R.id.card_Flavor3);
        fragMan = getFragmentManager();
        // Inflate the layout for this fragment
        databaseHelper = new DatabaseHelper(this.getContext());
        try {
            db = databaseHelper.getWritableDatabase();
            cursor = db.query("PROFILE",
                    new String[]{"NAME", "COINS", "PACKS"},null,null,null, null, null);
        }catch(SQLiteException se){
            Toast.makeText(this.getContext(), "Error with Database", Toast.LENGTH_SHORT).show();
        }
        if(cursor.moveToFirst()){
            coinsCount = cursor.getInt(1);
            packCount = cursor.getInt(2);
            cursor.close();
        }

        buttonPackOpen = view.findViewById(R.id.buttonPackOpen);
        buttonPackOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(packCount > 0){
                    packCount--;
                    String packs = " = "+Integer.toString(packCount);
                    packNumber.setText(packs);
                    //update database
                    ContentValues newValues = new ContentValues();
                    newValues.put("PACKS",packCount);
                    db.update("PROFILE",newValues,null,null);
                    packOpen = new PackOpenFragment();
                    fragMan.beginTransaction().replace(R.id.profile_Fragment_Container,packOpen).commit();

                }else{
                    Toast.makeText(v.getContext(), "No Packs to Open! Visit the Shop!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        coinNumber = view.findViewById(R.id.textCoinCountProfile);
        String coins = " = "+Integer.toString(coinsCount);
        coinNumber.setText(coins);
        packNumber = view.findViewById(R.id.textPackCountProfile);
        String packs = " = "+Integer.toString(packCount);
        packNumber.setText(packs);

        packOpen = new PackOpenFragment();
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
