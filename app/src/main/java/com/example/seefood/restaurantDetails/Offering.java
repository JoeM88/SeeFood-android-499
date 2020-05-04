package com.example.seefood.restaurantDetails;

import com.example.seefood.models.MealModel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Offering extends ExpandableGroup<MealModel> {
    public Offering(String title, List<MealModel> items)
    {
        super(title, items);
    }

}
