package com.example.csadmin.homework4;

import android.content.Context;

public class Card {

    String name;
    int atk;
    int hp;
    int rarity;
    int value;
    int count;
    String flavor1;
    String flavor2;
    String flavor3;
    int imageID;


    public Card(Context context, int name, int atk, int hp, int rarity, int value,
                int flavor1, int flavor2, int flavor3,
                int imageId){
        this.name = context.getResources().getString(name);
        this.atk = atk;
        this.hp = hp;
        this.rarity = rarity;
        this.value = value;
        this.flavor1 = context.getResources().getString(flavor1);
        this.flavor2 = context.getResources().getString(flavor2);
        this.flavor3 = context.getResources().getString(flavor3);
        this.imageID = imageId;
        count = 0;
    }

    public String getName(){
        return name;
    }

    public int getAtk() {
        return atk;
    }

    public int getRarity() {
        return rarity;
    }

    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public int getHp() {

        return hp;
    }

    public String getFlavor1() {
        return flavor1;
    }

    public String getFlavor2() {
        return flavor2;
    }

    public String getFlavor3() {
        return flavor3;
    }

    public int getImageID() {
        return imageID;
    }
}
