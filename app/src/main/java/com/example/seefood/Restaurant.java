package com.example.seefood;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Restaurant implements Parcelable {
    private String name;
    private String address;
    private int rating;
    private double distance;
    private int numReviews;
    private String category;
    private int image;


    public Restaurant(String name, String address, int rating, double distance, int numReviews, String category, int image) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.distance = distance;
        this.numReviews = numReviews;
        this.category = category;
        this.image = image;
    }


    protected Restaurant(Parcel in) {
        name = in.readString();
        address = in.readString();
        rating = in.readInt();
        distance = in.readDouble();
        numReviews = in.readInt();
        category = in.readString();
        image = in.readInt();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getRating() {
        return rating;
    }

    public double getDistance() {
        return distance;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public String getCategory() {
        return category;
    }

    public int getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(category);
        parcel.writeInt(rating);
        parcel.writeInt(numReviews);
        parcel.writeInt(image);

    }
}
