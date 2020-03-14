package com.example.seefood

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.junit.runner.RunWith

class profileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.default_profile_fragment, FragmentProfile())
        ft.replace(R.id.default_profile_fragment, FragmentProfile())
    }

    fun goHome(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}