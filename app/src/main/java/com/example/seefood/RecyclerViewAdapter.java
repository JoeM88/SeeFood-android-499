package com.example.seefood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seefood.RecyclerViewAdapter.MyViewHolder
import org.junit.runner.RunWith

class RecyclerViewAdapter(var mContext: Context?, var mData: MutableList<Restaurant?>?) : RecyclerView.Adapter<MyViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View?
        v = LayoutInflater.from(mContext).inflate(R.layout.restaurnt_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.restaurantName.setText(mData.get(position).getName())
        holder.distance.setText(mData.get(position).getDistance().toString() + " mi")
        holder.address.setText(mData.get(position).getAddress())
        holder.category.setText(mData.get(position).getCategory())
        holder.rate.setRating(mData.get(position).getRating().toFloat())
        holder.img.setImageResource(mData.get(position).getImage())
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val restaurantName: TextView?
        private val address: TextView?
        private val distance: TextView?
        private val category: TextView?
        private val rate: RatingBar?
        private val img: ImageView?

        init {
            restaurantName = itemView.findViewById<View?>(R.id.restaurant_name) as TextView?
            address = itemView.findViewById<View?>(R.id.address) as TextView?
            distance = itemView.findViewById<View?>(R.id.distance) as TextView?
            category = itemView.findViewById<View?>(R.id.category) as TextView?
            rate = itemView.findViewById<View?>(R.id.ratingBar) as RatingBar?
            img = itemView.findViewById<View?>(R.id.restaurant_img) as ImageView?
        }
    }

}