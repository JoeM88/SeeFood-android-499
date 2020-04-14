package com.example.seefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private OnRestaurantListener mOnRestaurantListener;
    private List<Restaurant> mData;

    public RecyclerViewAdapter(Context myContext, List<Restaurant> mData, OnRestaurantListener mOnRestaurantListener) {
        this.mContext = myContext;
        this.mData = mData;
        this.mOnRestaurantListener = mOnRestaurantListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.restaurnt_item, parent, false);
        return new MyViewHolder(v, mOnRestaurantListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.restaurantName.setText(mData.get(position).getName());
        holder.distance.setText(String.format("%s mi", mData.get(position).getDistance()));
        holder.address.setText(mData.get(position).getAddress());
        holder.category.setText(mData.get(position).getCategory());
        holder.rate.setRating(mData.get(position).getRating());
        holder.img.setImageResource(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView restaurantName;
        private TextView address;
        private TextView distance;
        private TextView category;
        private RatingBar rate;
        private ImageView img;
        OnRestaurantListener onRestaurantListener;

        public MyViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            address = itemView.findViewById(R.id.address);
            distance = itemView.findViewById(R.id.distance);
            category = itemView.findViewById(R.id.category);
            rate = itemView.findViewById(R.id.ratingBar);
            img = itemView.findViewById(R.id.restaurant_img);
            this.onRestaurantListener = onRestaurantListener;

            itemView.setOnClickListener(this);


        }
        public void onClick(View view)
        {
             onRestaurantListener.onRestaurantClick(getAdapterPosition());
        }

    }
    public interface OnRestaurantListener{
        void onRestaurantClick(int position);
    }

}
