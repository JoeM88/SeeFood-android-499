package com.example.seefood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.junit.runner.RunWith

/**
 * A simple [Fragment] subclass.
 */
class ownerFragment : Fragment() {
    private var v: View? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_owner, container, false)
        return v
    }
}