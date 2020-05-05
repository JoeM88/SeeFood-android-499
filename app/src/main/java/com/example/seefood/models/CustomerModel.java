package com.example.seefood.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CustomerModel implements Parcelable {
    private String diplayName;
    private String displayID;
    private String photoName;
    private String photoUrl;
    private ArrayList<String> favorites;
    private ArrayList<String> recentPlaces;

    public CustomerModel(){
        //Empty constructor
    }

    public CustomerModel(String dname, ArrayList<String> favs, String pname, String purl, ArrayList<String> recent){
        this.diplayName = dname;
        this.favorites = favs;
        this.photoName = pname;
        this.photoUrl = purl;
        this.recentPlaces = recent;
    }

    protected CustomerModel(Parcel in) {
        diplayName = in.readString();
        displayID = in.readString();
        photoName = in.readString();
        photoUrl = in.readString();
        favorites = in.createStringArrayList();
        recentPlaces = in.createStringArrayList();
    }

    public static final Creator<CustomerModel> CREATOR = new Creator<CustomerModel>() {
        @Override
        public CustomerModel createFromParcel(Parcel in) {
            return new CustomerModel(in);
        }

        @Override
        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };

    public String getDiplayName() {
        return diplayName;
    }

    public void setDiplayName(String diplayName) {
        this.diplayName = diplayName;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ArrayList<String> getRecentPlaces() {
        return recentPlaces;
    }

    public void setRecentPlaces(ArrayList<String> recentPlaces) {
        this.recentPlaces = recentPlaces;
    }

    public String getDisplayID() {
        return displayID;
    }

    public void setDisplayID(String displayID) {
        this.displayID = displayID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(diplayName);
        dest.writeString(displayID);
        dest.writeString(photoName);
        dest.writeString(photoUrl);
        dest.writeStringList(favorites);
        dest.writeStringList(recentPlaces);
    }

}