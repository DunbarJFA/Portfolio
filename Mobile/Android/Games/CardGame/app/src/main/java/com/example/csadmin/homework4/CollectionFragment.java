package com.example.csadmin.homework4;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class CollectionFragment extends Fragment {
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ListView cardList;
    SimpleCursorAdapter listAdaptor;

    public CollectionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CollectionFragment newInstance(String param1, String param2) {
        CollectionFragment fragment = new CollectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        cardList = view.findViewById(R.id.listCards);

        databaseHelper = new DatabaseHelper(this.getContext());
        try{
            db = databaseHelper.getReadableDatabase();
        }catch(SQLiteException se){
            Toast.makeText(this.getContext(), "Database Unavailable", Toast.LENGTH_SHORT).show();
        }
        cursor = db.query("CARD",new String[]{"_id","NAME","COUNT"},
                null,null,null,null,null);
        listAdaptor = new SimpleCursorAdapter(getContext(),android.R.layout.two_line_list_item, cursor, new String[]{"NAME" , "COUNT"},new int[]{android.R.id.text1, android.R.id.text2},0);
        cardList.setAdapter(listAdaptor);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cursor.close();
        db.close();
    }
}
