package com.example.seefood

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.runner.RunWith

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botNav = findViewById<BottomNavigationView?>(R.id.bottom_nav)
        botNav.setOnNavigationItemSelectedListener(navListener)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container_fragment, FragmentList())
        ft.commit()
    }

    fun viewProfile(view: View?) {
        val intent = Intent(this, profileActivity::class.java)
        //put extra if needed?
        startActivity(intent)
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener? = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_home -> selectedFragment = FragmentList()
            R.id.nav_favorite -> selectedFragment = FragmentFavorite()
            R.id.nav_profile -> {
                selectedFragment = FragmentProfile()
                //                    break;
                viewProfile(null)
            }
        }
        assert(selectedFragment != null)
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, selectedFragment).commit()
        true
    }
}