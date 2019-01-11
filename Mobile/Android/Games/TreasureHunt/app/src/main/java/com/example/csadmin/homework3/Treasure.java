package com.example.csadmin.homework3;

import android.os.Parcel;
import android.os.Parcelable;

public class Treasure implements Parcelable {
    private int value;
    private String name;
    private String rarity;

    public Treasure(String name, int value, String rarity){
        this.name = name;
        this.value = value;
        this.rarity = rarity;
    }

    @SuppressWarnings("unchecked")
    public Treasure(Parcel in){
        value = in.readInt();
        name = in.readString();
        rarity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(value);
        dest.writeString(name);
        dest.writeString(rarity);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<Treasure> CREATOR = new Creator<Treasure>() {
        @Override
        public Treasure createFromParcel(Parcel source) {
            return new Treasure(source);
        }

        @Override
        public Treasure[] newArray(int size) {
            return new Treasure[size];
        }
    };

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }
}
