package com.example.csadmin.homework3;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import java.util.ArrayList;

public class Hunter implements Parcelable {
    private String name;
    private String portraitID;
    private String spriteID;
    private int health;
    private Integer healthLeft;
    private int stamina;
    private Integer staminaLeft;
    private int luck;
    private int specialty;//index number related to an array of environmental specialties (aquatic, subterranean, dark, none, urban, desert, etc) grants buffs
    private int dead;
    private ArrayList<Treasure> treasure;//contains treasure object IDs

    public Hunter(String name, String portraitID,String spriteID, int health, int stamina, int luck, int specialty){
        this.name = name;
        this.portraitID = portraitID;
        this.spriteID = spriteID;
        this.health = health;
        healthLeft = health;
        this.stamina = stamina;
        staminaLeft = stamina;
        this.luck = luck;
        this.specialty = specialty;
        dead = 0;
        treasure = new ArrayList<>(3);
    }//end of constructor

    @SuppressWarnings("unchecked")
    public Hunter(Parcel in){
        name = in.readString();
        portraitID = in.readString();
        spriteID = in.readString();
        health = in.readInt();
        healthLeft = in.readInt();
        stamina = in.readInt();
        staminaLeft = in.readInt();
        luck = in.readInt();
        specialty = in.readInt();
        dead = in.readInt();
        treasure = in.readArrayList(Treasure.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeString(portraitID);
        dest.writeString(spriteID);
        dest.writeInt(health);
        dest.writeInt(healthLeft);
        dest.writeInt(stamina);
        dest.writeInt(staminaLeft);
        dest.writeInt(luck);
        dest.writeInt(specialty);
        dest.writeInt(dead);
        dest.writeList(treasure);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<Hunter> CREATOR = new Creator<Hunter>() {
        @Override
        public Hunter createFromParcel(Parcel source) {
            return new Hunter(source);
        }

        @Override
        public Hunter[] newArray(int size) {
            return new Hunter[size];
        }
    };


    public String getPortraitID() {
        return portraitID;
    }

    public String getSpriteID() {
        return spriteID;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return healthLeft;
    }

    public int getTotalHealth(){
        return health;
    }

    public Integer getStamina() {
        return staminaLeft;
    }

    public int getTotalStamina(){
        return stamina;
    }

    public int getLuck() {
        return luck;
    }

    public int getSpecialty() {
        return specialty;
    }

    public int getDead(){
        return dead;
    }

    public ArrayList<Treasure> getTreasure() {
        return treasure;
    }

    public void sellTreasure(){
        for(Treasure t : treasure) {
            MainActivity.setGoldCount(t.getValue());
        }
        treasure.clear();
    }

    public void setHealed(){
        healthLeft = health;
    }

    public void setRested(){
        staminaLeft = stamina;
    }

    public void setStamina(int spent){
        this.staminaLeft -= spent;
    }

    public void setHealth(int damage){
        this.healthLeft -= damage;
    }

    public void setDead(){
        //replace sprite and portrait with death versions and prevent from healing/resting
        dead = 1;
    }
}//end of class Hunter
