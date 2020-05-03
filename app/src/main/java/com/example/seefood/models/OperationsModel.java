package com.example.seefood.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OperationsModel implements Parcelable {
    private int openHour;
    private int openMins;
    private int durHours;
    private int durMins;

    public OperationsModel(){/*Empty Constructor*/}

    public OperationsModel(int openHour, int openMins, int durHours, int durMins){
        this.openHour = openHour;
        this.openMins = openMins;
        this.durHours = durHours;
        this.durMins = durMins;
    }
    public OperationsModel(Parcel in){
        openHour = in.readInt();
        openMins = in.readInt();
        durHours = in.readInt();
        durMins = in.readInt();
    }

    public static final Creator<OperationsModel> CREATOR = new Creator<OperationsModel>() {
        @Override
        public OperationsModel createFromParcel(Parcel in) {
            return new OperationsModel(in);
        }

        @Override
        public OperationsModel[] newArray(int size) {
            return new OperationsModel[size];
        }
    };

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getOpenMins() {
        return openMins;
    }

    public void setOpenMins(int openMins) {
        this.openMins = openMins;
    }

    public int getDurHours() {
        return durHours;
    }

    public void setDurHours(int durHours) {
        this.durHours = durHours;
    }

    public int getDurMins() {
        return durMins;
    }

    public void setDurMins(int durMins) {
        this.durMins = durMins;
    }

    public String toString(){
        return "Hours open: " + openHour + ":" + openMins + "\nDuring Hours: " + durHours + ":" + durMins;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(openHour);
        parcel.writeInt(openMins);
        parcel.writeInt(durHours);
        parcel.writeInt(durMins);
    }
}
