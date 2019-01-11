package com.example.csadmin.homework4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String dbName = "cardDB";
    private static final int version = 1;
    private Context context;

    public DatabaseHelper(Context context){
        super(context, dbName, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,oldVersion,newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion, int newVersion){
        if(oldVersion<2){
            //create card database
            db.execSQL("CREATE TABLE CARD(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ATK INTEGER,HP INTEGER,RARITY INTEGER,VALUE INTEGER,FLAVOR1 TEXT,FLAVOR2 TEXT,FLAVOR3 TEXT,IMAGE_ID INTEGER,COUNT INTEGER);");
            //create card data
            ArrayList<Card> cards = createCards(context);
            //insert card data
            for (Card card : cards){
                insertCard(db,card);
            }

            //create profile database
            db.execSQL("CREATE TABLE PROFILE("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT,"
                    + "COINS INTEGER,"
                    + "PACKS INTEGER);");
            ContentValues profileValues = new ContentValues();
            String name = context.getResources().getString(R.string.player_name);
            profileValues.put("NAME",name);
            profileValues.put("COINS",500);
            profileValues.put("PACKS",10);
            db.insert("PROFILE",null,profileValues);
        }else if(oldVersion < 3){
            //handle eventual database upgrade
        }
    }



    public ArrayList<Card> createCards(Context context){
        int imageId = R.drawable.mugrus_portrait;

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(context,R.string.Collection_Mahdi,10,10,5,1200,
                R.string.Collection_MahdiFlavor1,R.string.Collection_MahdiFlavor2,
                R.string.Collection_MahdiFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Lisa,8,12,5,1200,
                R.string.Collection_LisaFlavor1,R.string.Collection_LisaFlavor2,
                R.string.Collection_LisaFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Tu,7,8,4,800,
                R.string.Collection_TuFlavor1,R.string.Collection_TuFlavor2,
                R.string.Collection_TuFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_DePano,8,7,4,800,
                R.string.Collection_DePanoFlavor1,R.string.Collection_DePanoFlavor2,
                R.string.Collection_DePanoFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Roussev,9,6,4,800,
                R.string.Collection_RoussevFlavor1,R.string.Collection_RoussevFlavor2,
                R.string.Collection_RoussevFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Summa,6,9,4,800,
                R.string.Collection_SummaFlavor1,R.string.Collection_SummaFlavor2,
                R.string.Collection_SummaFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Matt,6,6,3,300,
                R.string.Collection_MattFlavor1,R.string.Collection_MattFlavor2,
                R.string.Collection_MattFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Ted,3,9,3,300,
                R.string.Collection_TedFlavor1,R.string.Collection_TedFlavor2,
                R.string.Collection_TedFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Samuel,5,7,3,300,
                R.string.Collection_SamuelFlavor1,R.string.Collection_SamuelFlavor2,
                R.string.Collection_SamuelFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Ware,9,3,3,300,
                R.string.Collection_WareFlavor1,R.string.Collection_WareFlavor2,
                R.string.Collection_WareFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Sylve,7,5,3,300,
                R.string.Collection_SylveFlavor1,R.string.Collection_SylveFlavor2,
                R.string.Collection_SylveFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Aaron,2,5,2,50,
                R.string.Collection_AaronFlavor1,R.string.Collection_AaronFlavor2,
                R.string.Collection_AaronFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Hiram,3,4,2,50,
                R.string.Collection_HiramFlavor1,R.string.Collection_HiramFlavor2,
                R.string.Collection_HiramFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Haydar,3,3,2,50,
                R.string.Collection_HaydarFlavor1,R.string.Collection_HaydarFlavor2,
                R.string.Collection_HaydarFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Manish,2,5,2,50,
                R.string.Collection_ManishFlavor1,R.string.Collection_ManishFlavor2,
                R.string.Collection_ManishFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Nehal,6,1,2,50,
                R.string.Collection_NehalFlavor1,R.string.Collection_NehalFlavor2,
                R.string.Collection_NehalFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Hedge,1,6,2,50,
                R.string.Collection_HedgeFlavor1,R.string.Collection_HedgeFlavor2,
                R.string.Collection_HedgeFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Kyle,4,3,2,50,
                R.string.Collection_KyleFlavor1,R.string.Collection_KyleFlavor2,
                R.string.Collection_KyleFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Amanda,2,5,2,50,
                R.string.Collection_AmandaFlavor1,R.string.Collection_AmandaFlavor2,
                R.string.Collection_AmandaFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Nolan,2,3,1,10,
                R.string.Collection_NolanFlavor1,R.string.Collection_NolanFlavor2,
                R.string.Collection_NolanFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Chris,3,2,1,10,
                R.string.Collection_ChrisFlavor1,R.string.Collection_ChrisFlavor2,
                R.string.Collection_ChrisFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Noah,4,1,1,10,
                R.string.Collection_NoahFlavor1,R.string.Collection_NoahFlavor2,
                R.string.Collection_NoahFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Chase,2,3,1,10,
                R.string.Collection_ChaseFlavor1,R.string.Collection_ChaseFlavor2,
                R.string.Collection_ChaseFlavor3,imageId));
        cards.add(new Card(context,R.string.Collection_Rabble,1,1,0,1,
                R.string.Collection_RabbleFlavor1,R.string.Collection_RabbleFlavor2,
                R.string.Collection_RabbleFlavor3,imageId));
        return cards;
    }

    public void insertCard(SQLiteDatabase db, Card card){
        //Create ContentValues object for a card
        ContentValues cardValues = new ContentValues();
        cardValues.put("NAME",card.getName());
        cardValues.put("ATK",card.getAtk());
        cardValues.put("HP",card.getHp());
        cardValues.put("RARITY",card.getRarity());
        cardValues.put("VALUE",card.getValue());
        cardValues.put("FLAVOR1",card.getFlavor1());
        cardValues.put("FLAVOR2",card.getFlavor2());
        cardValues.put("FLAVOR3",card.getFlavor3());
        cardValues.put("IMAGE_ID",card.getImageID());
        cardValues.put("COUNT",card.getCount());
        //then insert that card data into the table
        db.insert("CARD",null,cardValues);
    }
}
