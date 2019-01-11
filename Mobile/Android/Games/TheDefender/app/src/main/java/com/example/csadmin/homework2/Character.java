package com.example.csadmin.homework2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Character implements Parcelable {
    private String name;
    private int strength;
    private int intelligence;
    private int health;
    private int luck;
    private int gold;
    private String dropName;
    private int dropValue;

    public Character(String n, String dn, int dv){
        //Random rand = new Random();
        name = n;
        dropName = dn;
        dropValue = dv;
        strength = 17;
        intelligence = 17;
        health = 300;
        luck = 0;
        gold = 3;
    }
    public Character(String n, int g){
        name = n;
        gold = g;
        strength = 10;
        intelligence = 4;
        health = 400;
        luck = 0;
        dropName = "none";
        dropValue = 0;
    }

    protected Character(Parcel in) {
        name = in.readString();
        strength = in.readInt();
        intelligence = in.readInt();
        health = in.readInt();
        luck = in.readInt();
        gold = in.readInt();
        dropName = in.readString();
        dropValue = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(strength);
        dest.writeInt(intelligence);
        dest.writeInt(health);
        dest.writeInt(luck);
        dest.writeInt(gold);
        dest.writeString(dropName);
        dest.writeInt(dropValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    protected void setStrength(int s){
        this.strength = s;
    }
    protected void setIntelligence(int i){
        this.intelligence = i;
    }
    protected void setHealth(int h){
        this.health = h;
    }
    protected void setLuck(int l){
        this.luck = l;
    }
    protected void setGold(int g){
        this.gold = g;
    }
    protected void setName(String n){
        this.name = n;
    }
    protected void setDropName(String d){
        this.dropName = d;
    }
    protected void setDropValue(int d){
        this.dropValue = d;
    }
    public int getStrength(){
        return this.strength;
    }
    public int getIntelligence(){
        return this.intelligence;
    }
    public int getHealth(){
        return health;
    }
    public int getLuck(){
        return this.luck;
    }
    public int getGold(){
        return gold;
    }
    public int getDropValue(){
        return this.dropValue;
    }
    public String getName(){
        return this.name;
    }
    public String getDropName(){
        return this.dropName;
    }

}
