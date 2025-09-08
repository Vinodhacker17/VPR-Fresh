package com.example.blinkitclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener {
            lateinit var selectedFragment: Fragment
            when (it.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_categories -> selectedFragment = CategoriesFragment()
                R.id.nav_orders -> selectedFragment = OrdersFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            true
        }

        // Set the default fragment to be the HomeFragment when the app starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }
    }
}

