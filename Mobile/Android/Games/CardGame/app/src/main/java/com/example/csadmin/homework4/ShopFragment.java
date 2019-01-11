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
import android.widget.Button;
import android.widget.Toast;

public class ShopFragment extends Fragment {
    Button buttonBuyPacks;
    Button buttonGetCoins;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    int coinsCount;
    int packCount;

    public ShopFragment() {
        // Required empty public constructor
    }


    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(this.getContext());
        try {
            db = databaseHelper.getWritableDatabase();
            cursor = db.query("PROFILE",
                    new String[]{"NAME", "COINS", "PACKS"},null,null,null, null, null);
        }catch(SQLiteException se){
            Toast.makeText(this.getContext(), "Error with Database", Toast.LENGTH_SHORT).show();
        }
        if(cursor.moveToFirst()) {
            coinsCount = cursor.getInt(1);
            packCount = cursor.getInt(2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        buttonBuyPacks = view.findViewById(R.id.buttonBuy);
        buttonBuyPacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues newValues = new ContentValues();
                if(coinsCount >= 100){
                    packCount++;
                    coinsCount -= 100;
                    Toast.makeText(v.getContext(), "100 Gold, plz!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Not enough coins!", Toast.LENGTH_SHORT).show();
                }
                newValues.put("PACKS",packCount);
                newValues.put("COINS", coinsCount);
                db.update("PROFILE",newValues,null,null);
            }
        });
        buttonGetCoins = view.findViewById(R.id.buttonCoins);
        buttonGetCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinsCount += 50;
                ContentValues newValues = new ContentValues();
                newValues.put("COINS", coinsCount);
                db.update("PROFILE",newValues,null,null);
                Toast.makeText(v.getContext(), "Fat Stacks Aquired", Toast.LENGTH_SHORT).show();
            }
        });

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
