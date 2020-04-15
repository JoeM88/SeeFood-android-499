package com.example.seefood;

import java.util.ArrayList;

public class CustomerModel {
    private String diplayName;
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
}
