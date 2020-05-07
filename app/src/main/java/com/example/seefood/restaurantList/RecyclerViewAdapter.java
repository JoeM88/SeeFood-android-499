package com.example.seefood.restaurantList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seefood.R;
import com.example.seefood.models.RestaurantModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private OnRestaurantListener mOnRestaurantListener;
    private List<RestaurantModel> mData;

    public RecyclerViewAdapter(Context myContext, List<RestaurantModel> mData, OnRestaurantListener mOnRestaurantListener) {
        this.mContext = myContext;
        this.mData = mData;
        this.mOnRestaurantListener = mOnRestaurantListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.restaurant_item, parent, false);
        return new MyViewHolder(v, mOnRestaurantListener);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.restName.setText(mData.get(position).getRestName());
        holder.streetAddress.setText(mData.get(position).getStreetAddress());
        Picasso.get()
                .load(mData.get(position).getPhotoURL()).into(holder.photoURL);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView restName;
        public TextView streetAddress;
        public ImageView photoURL;
        OnRestaurantListener onRestaurantListener;

        public MyViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener) {
            super(itemView);
            restName = itemView.findViewById(R.id.restaurant_name);
            streetAddress = itemView.findViewById(R.id.address);
            photoURL = itemView.findViewById(R.id.restaurant_img);
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
