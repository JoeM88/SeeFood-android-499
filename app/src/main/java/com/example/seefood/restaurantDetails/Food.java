package com.example.seefood.restaurantDetails;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    private String name;
    private String calories;
    private String foodName;
    private String foodPhotoUrl;


    public Food(String name, String calories, String photoName, String foodPhotoUrl){
        this.name = name;
        this.calories = calories;
        this.foodName = photoName;
        this.foodPhotoUrl = foodPhotoUrl;

    }
    protected Food(Parcel in) {
        name = in.readString();
        calories = in.readString();
        foodName = in.readString();
        foodPhotoUrl = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setPhotoName(String foodName) {
        this.foodName = foodName;
    }

    public String getPhotoUrl() {
        return foodPhotoUrl;
    }

    public void setPhotoURL(String foodPhotoUrl) {
        this.foodPhotoUrl = foodPhotoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(foodName);
        parcel.writeString(foodPhotoUrl);
        parcel.writeString(calories);

    }


}
