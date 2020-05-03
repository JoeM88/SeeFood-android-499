package com.example.seefood.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class MealModel implements Parcelable {
    private String name;
    private String calories;
    private String photoName;
    private String photoURL;
    private String type;
    private String description;
    private HashMap<String, Boolean> allergies;

    public MealModel(){ /*empty constructor*/ }

    public MealModel(String name, String calories, String photoName, String photoURL, String type, String description, HashMap<String, Boolean> allergies){
        this.name = name;
        this.calories = calories;
        this.photoName = photoName;
        this.photoURL = photoURL;
        this.type = type;
        this.description = description;
        this.allergies = allergies;
    }

    protected MealModel(Parcel in) {
        name = in.readString();
        calories = in.readString();
        photoName = in.readString();
        photoURL = in.readString();
        type = in.readString();
        description = in.readString();
        in.readMap(allergies, HashMap.class.getClassLoader());
    }

    public static final Creator<MealModel> CREATOR = new Creator<MealModel>() {
        @Override
        public MealModel createFromParcel(Parcel in) {
            return new MealModel(in);
        }

        @Override
        public MealModel[] newArray(int size) {
            return new MealModel[size];
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Boolean> getAllergies() {
        return allergies;
    }

    public void setAllergies(HashMap<String, Boolean> allergies) {
        this.allergies = allergies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(calories);
        parcel.writeString(photoName);
        parcel.writeString(photoURL);
        parcel.writeString(type);
        parcel.writeString(description);
        parcel.writeMap(allergies);
    }
}
