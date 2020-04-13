package com.example.seefood;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantModel {
    public String restName;
    public String owner;
    public String streetAddress;
    public String state;
    public String zipCode;
    public String phoneNumber;
    public String city;
    public String photoName;
    public String photoURL;

    //meal offerings
    public HashMap<String, ArrayList<String>> offerings;
    public ArrayList<HashMap<String, HashMap<String, Integer>>> hoursOperation;

    //TODO: Logo and Photo, must be via camera or file explorer in android studio

    public RestaurantModel()
    {
        //empty constructor
    }

    public RestaurantModel(String restName, String owner, String streetAddress, String state, String zipCode,
                           String city, String phoneNumber, HashMap<String, ArrayList<String>> offerings,
                           ArrayList<HashMap<String, HashMap<String, Integer>>> hoursOperation, String photoName, String photoURL)
    {
        this.restName = restName;
        this.owner = owner;
        this.streetAddress = streetAddress;
        this.state = state;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.offerings = offerings;
        this.hoursOperation = hoursOperation;
        this.photoName = photoName;
        this.photoURL = photoURL;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HashMap<String, ArrayList<String>> getOfferings() {
        return offerings;
    }

    public void setOfferings(HashMap<String, ArrayList<String>> offerings) {
        this.offerings = offerings;
    }

    public ArrayList<HashMap<String, HashMap<String, Integer>>> getHoursOperation() {
        return hoursOperation;
    }

    public void setHoursOperation(ArrayList<HashMap<String, HashMap<String, Integer>>> hoursOperation) {
        this.hoursOperation = hoursOperation;
    }
}