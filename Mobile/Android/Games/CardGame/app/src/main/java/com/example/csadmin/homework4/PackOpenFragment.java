package com.example.csadmin.homework4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class PackOpenFragment extends Fragment {
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    TextView cardName;
    TextView cardAtk;
    TextView cardHp;
    TextView cardRarity;
    TextView flavor1;
    TextView flavor2;
    TextView flavor3;
    int cardCount;

    public PackOpenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pack_open, container, false);
        cardName = view.findViewById(R.id.card_Title);
        cardAtk = view.findViewById(R.id.card_Atk);
        cardHp = view.findViewById(R.id.card_HP);
        cardRarity = view.findViewById(R.id.card_Rarity);
        flavor1 = view.findViewById(R.id.card_Flavor1);
        flavor2 = view.findViewById(R.id.card_Flavor2);
        flavor3 = view.findViewById(R.id.card_Flavor3);

        databaseHelper = new DatabaseHelper(this.getContext());
        try {
            db = databaseHelper.getWritableDatabase();
           }catch(SQLiteException se){
            Toast.makeText(this.getContext(), "Error with Database", Toast.LENGTH_SHORT).show();
        }
        //pick random card from db
        Random rand = new Random();
        int cardPicked = rand.nextInt(24)+1;
        cursor = db.query("CARD",
                new String[]{"NAME",
                        "ATK", "HP", "RARITY",
                        "FLAVOR1", "FLAVOR2", "FLAVOR3",
                        "COUNT"}, "_id = ?",
                new String[]{Integer.toString(cardPicked)},
                null, null, null);
        if(cursor.moveToFirst()){
            String name = cursor.getString(0);
            cardName.setText(name);
            cardAtk.setText(cursor.getString(1));
            cardHp.setText(cursor.getString(2));
            cardRarity.setText(cursor.getString(3));
            flavor1.setText(cursor.getString(4));
            flavor2.setText(cursor.getString(5));
            flavor3.setText(cursor.getString(6));
            cardCount = cursor.getInt(7);
        }
        ContentValues newCard = new ContentValues();
        newCard.put("COUNT",++cardCount);
        System.out.println(cardCount);
        db.update("CARD",newCard,"_id = ?",new String[]{Integer.toString(cardPicked)});

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
