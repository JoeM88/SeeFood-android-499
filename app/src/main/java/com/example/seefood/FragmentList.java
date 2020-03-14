package com.example.seefood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.junit.runner.RunWith
import java.util.*

class FragmentList : Fragment() {
    private var v: View? = null
    private var myRecyclerView: RecyclerView? = null
    private var lstRestaurant: MutableList<Restaurant?>? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.list_fragment, container, false)
        myRecyclerView = v.findViewById(R.id.restaurant_recyclerview)
        val recycleAdapter = RecyclerViewAdapter(context, lstRestaurant)
        myRecyclerView.setLayoutManager(LinearLayoutManager(activity))
        myRecyclerView.setAdapter(recycleAdapter)
        myRecyclerView.addItemDecoration(DividerItemDecoration(myRecyclerView.getContext(), DividerItemDecoration.VERTICAL))
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //String name, String address, int rating, double distance, int numReviews, String category
        lstRestaurant = ArrayList()
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
        lstRestaurant.add(Restaurant("McDonalds", "123 Main St.", 3, 21.5, 45, "fastfood", R.drawable.mcdonalds))
    }
}