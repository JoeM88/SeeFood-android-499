package com.example.seefood.restaurantDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seefood.R;
import com.example.seefood.models.MealModel;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class OfferingAdapter extends ExpandableRecyclerViewAdapter<MealsViewHolder, FoodViewHolder> {


    public OfferingAdapter(List<? extends ExpandableGroup> groups){
        super(groups);
    }
    @Override
    public MealsViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_meal_item, parent, false);
        return new MealsViewHolder(view);
    }

    @Override
    public FoodViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(FoodViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final MealModel meal = ((Offering)group).getItems().get(childIndex);
        holder.onBind(meal);
    }

    @Override
    public void onBindGroupViewHolder(MealsViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setMealName(group);
    }
}
