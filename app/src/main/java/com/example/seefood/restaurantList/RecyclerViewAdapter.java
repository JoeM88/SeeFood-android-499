package com.example.seefood.restaurantList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private OnRestaurantListener mOnRestaurantListener;
    private OnRestaurantLikeListener mOnRestaurantLikeListener;
    private List<RestaurantModel> mData;

    public RecyclerViewAdapter(Context myContext, List<RestaurantModel> mData, OnRestaurantListener mOnRestaurantListener, OnRestaurantLikeListener mOnRestaurantLikeListener) {
        this.mContext = myContext;
        this.mData = mData;
        this.mOnRestaurantListener = mOnRestaurantListener;
        this.mOnRestaurantLikeListener = mOnRestaurantLikeListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.restaurant_item, parent, false);
        return new MyViewHolder(v, mOnRestaurantListener, mOnRestaurantLikeListener);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.restName.setText(mData.get(position).getRestName());
        String fullAddress = mData.get(position).getStreetAddress() + ", " + mData.get(position).getCity() + ", " + mData.get(position).getState() + " " + mData.get(position).getZipCode();
        holder.streetAddress.setText(fullAddress);
        holder.restPhone.setText(mData.get(position).getPhoneNumber());
        Picasso.get()
                .load(mData.get(position).getPhotoURL()).into(holder.photoURL);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.restaurant_name) TextView restName;
        @BindView(R.id.address) TextView streetAddress;
        @BindView(R.id.restaurant_img) ImageView photoURL;
        @BindView(R.id.restaurant_phone) TextView restPhone;
        @BindView(R.id.like_button) ImageView like_button;

        OnRestaurantListener onRestaurantListener;
        OnRestaurantLikeListener onRestaurantLikeListener;

        public MyViewHolder(@NonNull View itemView, OnRestaurantListener onRestaurantListener, OnRestaurantLikeListener onRestaurantLikeListener) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            this.onRestaurantListener = onRestaurantListener;
            this.onRestaurantLikeListener = onRestaurantLikeListener;

            itemView.setOnClickListener(this);
            like_button.setOnClickListener(likeListener);
        }

        private View.OnClickListener likeListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestaurantLikeListener.onRestaurantLikeClicked(getAdapterPosition(), like_button);
            }
        };

        public void onClick(View view) {
             onRestaurantListener.onRestaurantClick(getAdapterPosition());
        }

    }

    public interface OnRestaurantListener{
        void onRestaurantClick(int position);
    }

    public interface OnRestaurantLikeListener {
        void onRestaurantLikeClicked(int position, ImageView img);
    }

}
