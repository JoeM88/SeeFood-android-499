package com.example.seefood.restaurantDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seefood.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

public class MealsAdapter extends ExpandableRecyclerViewAdapter<MealsViewHolder, FoodViewHolder> {


    public MealsAdapter(List<? extends ExpandableGroup> groups){
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
        final Food food = ((Meals)group).getItems().get(childIndex);
        holder.onBind(food);
    }

    @Override
    public void onBindGroupViewHolder(MealsViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setMealName(group);
    }
}
