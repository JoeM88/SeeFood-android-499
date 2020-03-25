package com.example.seefood;

import java.util.ArrayList;

public class CustomerModel {
    public String diplayName;
    public ArrayList<String> favorites;

    public CustomerModel(){
        //Empty constructor
    }

    public CustomerModel(String dname, ArrayList<String> favs){
        this.diplayName = dname;
        this.favorites = favs;
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
}
