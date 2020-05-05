package com.example.seefood.restaurantDetails;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seefood.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MealsViewHolder extends GroupViewHolder {
    private TextView mealName;
    private ImageView arrow;

    public MealsViewHolder(View itemView){
        super(itemView);
        mealName = itemView.findViewById(R.id.list_meal_item_name);
        arrow = itemView.findViewById(R.id.list_item_meal_arrow);

    }
    public void setMealName(ExpandableGroup group) {
        mealName.setText(group.getTitle());
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    @Override
    public void expand() {
        animateExpand();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

}
