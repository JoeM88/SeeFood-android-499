package com.example.seefood.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantModel implements Parcelable {
    public String restName;
    public String owner;
    public String streetAddress;
    public String state;
    public String zipCode;
    public String phoneNumber;
    public String city;
    public String photoName;
    public String photoURL;
    private HashMap<String, HashMap<String, OperationsModel>>hOps;
    public HashMap<String, ArrayList<MealModel>> offerings;

    //meal offerings
//    public HashMap<String, ArrayList<MealModel>> offerings;
////    private ArrayList<HashMap<String, HashMap<String, String>>> hoursOperation;
//    private HashMap<String, HashMap<String, OperationsModel>>hOps;

    //TODO: Logo and Photo, must be via camera or file explorer in android studio

    public RestaurantModel()
    {
        //empty constructor
    }

    public RestaurantModel(String restName, String owner, String streetAddress, String state, String zipCode,
                           String city, String phoneNumber, HashMap<String, ArrayList<MealModel>> offerings,
                           /*ArrayList<HashMap<String, HashMap<String, String>>> hoursOperation*/HashMap<String, HashMap<String, OperationsModel>> hOps, String photoName, String photoURL)
    {
        this.restName = restName;
        this.owner = owner;
        this.streetAddress = streetAddress;
        this.state = state;
        this.zipCode = zipCode;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.offerings = offerings;
//        this.hoursOperation = hoursOperation;
        this.hOps = hOps;
        this.photoName = photoName;
        this.photoURL = photoURL;
    }

    protected RestaurantModel(Parcel in) {
        restName = in.readString();
        owner = in.readString();
        streetAddress = in.readString();
        state = in.readString();
        zipCode = in.readString();
        city = in.readString();
        phoneNumber = in.readString();
        in.readMap(offerings, HashMap.class.getClassLoader());
        in.readMap(hOps, List.class.getClassLoader());
    }

    public static final Creator<RestaurantModel> CREATOR = new Creator<RestaurantModel>() {
        @Override
        public RestaurantModel createFromParcel(Parcel in) {
            return new RestaurantModel(in);
        }

        @Override
        public RestaurantModel[] newArray(int size) {
            return new RestaurantModel[size];
        }
    };

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

    public HashMap<String, ArrayList<MealModel>> getOfferings() {
        return offerings;
    }

    public void setOfferings(HashMap<String, ArrayList<MealModel>> offerings) {
        this.offerings = offerings;
    }

//    public ArrayList<HashMap<String, HashMap<String, String>>> getHoursOperation() {
//        return hoursOperation;
//    }
//
//    public void setHoursOperation(ArrayList<HashMap<String, HashMap<String, String>>> hoursOperation) {
//        this.hoursOperation = hoursOperation;
//    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public HashMap<String, HashMap<String, OperationsModel>> gethOps() {
        return hOps;
    }

    public void sethOps(HashMap<String, HashMap<String, OperationsModel>> hOps) {
        this.hOps = hOps;
    }

    public String printRest(RestaurantModel rm){
        return "Name --> " + rm.getRestName() + "\n" + "Address --> " + rm.getStreetAddress() + "\n" + "City --> " + rm.getCity() + "\n"
                + "State -->" + rm.getState() + "\n" + "Zip Code--> " + rm.getZipCode() + "\n" + "Phone Number --> " + rm.getPhoneNumber() + "\n" +
                rm.getOfferings() + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restName);
        dest.writeString(owner);
        dest.writeString(streetAddress);
        dest.writeString(state);
        dest.writeString(zipCode);
        dest.writeString(phoneNumber);
        dest.writeString(photoName);
        dest.writeString(city);
        dest.writeString(photoURL);
        dest.writeMap(hOps);
        dest.writeMap(offerings);

    }

}