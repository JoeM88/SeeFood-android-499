package com.example.seefood.restaurantDetails;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seefood.R;
import com.example.seefood.restaurantDetails.Food;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class FoodViewHolder extends ChildViewHolder {

        private TextView foodName;
        private ImageView foodPicture;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodPicture = itemView.findViewById(R.id.foodImage);
        }



        public void onBind(Food food) {
                foodName.setText(food.getFoodName());
                Picasso.get()
                        .load(food.getPhotoUrl()).fit().centerInside().into(foodPicture);
        }



}

