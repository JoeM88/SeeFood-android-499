package com.example.seefood.restaurantDetails;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Meals extends ExpandableGroup<Food> {
    public Meals(String title, List<Food> items)
    {
        super(title, items);
    }

}
