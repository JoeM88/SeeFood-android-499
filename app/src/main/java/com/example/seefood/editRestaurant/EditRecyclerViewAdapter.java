package com.example.seefood.editRestaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.models.MealModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditRecyclerViewAdapter extends RecyclerView.Adapter<EditRecyclerViewAdapter.MyEditViewHolder> {

    private List<MealModel> mData;

    public EditRecyclerViewAdapter(List<MealModel> myData) {
        mData = myData;

    }

    @Override
    public MyEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_menu_item, parent, false);
        return new MyEditViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull MyEditViewHolder holder, int position) {
        holder.mealName.setText(mData.get(position).getName());
        holder.mealDescription.setText(mData.get(position).getDescription());
        holder.mealCalories.setText(("Cal " + mData.get(position).getCalories()));
        //holder.allergies.setText(mData.get(position).getAllergies().toString());
        holder.allergies.setText(mData.get(position).allergyPrint());
        Picasso.get()
                .load(mData.get(position).getPhotoURL()).into(holder.mealImage);
        holder.editMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("foodType", mData.get(position).getType());
                MealModel thisMeal = mData.get(position);
                bundle.putParcelable("meal", thisMeal);
                bundle.putInt("indexPosition", position);
                AppCompatActivity myActivity = (AppCompatActivity) v.getContext();
                Fragment nextStep = new manageMenuItemFragment();
                nextStep.setArguments(bundle);
                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, nextStep).addToBackStack("PrevMenu").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyEditViewHolder extends RecyclerView.ViewHolder {
        public TextView mealName;
        public TextView mealDescription;
        public ImageView mealImage;
        public TextView allergies;
        public TextView mealCalories;
        public Button editMealButton;

        public MyEditViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealNameText);
            mealDescription = itemView.findViewById(R.id.mealDescription);
            mealImage = itemView.findViewById(R.id.mealImageView);
            allergies = itemView.findViewById(R.id.allergiesText);
            mealCalories = itemView.findViewById(R.id.mealCalories);
            editMealButton = itemView.findViewById(R.id.editButton);
        }
    }
}