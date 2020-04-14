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


    Context mContext;

    public RecyclerViewAdapter(Context myContext, List<Restaurant> mData) {
        this.mContext = myContext;
        this.mData = mData;
    }

    List<Restaurant> mData;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.restaurant_item, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);


        return vHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.restaurantName.setText(mData.get(position).getName());
        holder.distance.setText(String.valueOf(mData.get(position).getDistance()) + " mi");
        holder.address.setText(mData.get(position).getAddress());
        holder.category.setText(mData.get(position).getCategory());
        holder.rate.setRating(mData.get(position).getRating());
        holder.img.setImageResource(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView restaurantName;
        private TextView address;
        private TextView distance;
        private TextView category;
        private RatingBar rate;
        private ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = (TextView) itemView.findViewById(R.id.restaurant_name);
            address = (TextView) itemView.findViewById(R.id.address);
            distance = (TextView) itemView.findViewById(R.id.distance);
            category = (TextView) itemView.findViewById(R.id.category);
            rate = (RatingBar) itemView.findViewById(R.id.ratingBar);
            img = (ImageView) itemView.findViewById(R.id.restaurant_img);

        }
    }
}
