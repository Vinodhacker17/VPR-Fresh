package com.example.blinkitclone

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "order_updates"
    private lateinit var bottomNav: BottomNavigationView // Made this a class variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        bottomNav = findViewById(R.id.bottom_navigation) // Initialize the class variable
        bottomNav.setOnItemSelectedListener {
            lateinit var selectedFragment: Fragment
            when (it.itemId) {
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_categories -> selectedFragment = CategoriesFragment()
                R.id.nav_cart -> selectedFragment = OrdersFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            true
        }

        // Set the default fragment when the app first loads
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }

        updateCartBadge() // Call this to set the initial badge count
    }

    // --- NEW FUNCTION TO UPDATE THE CART BADGE ---
    fun updateCartBadge() {
        val cartItemCount = Cart.getItemCount()
        // Get or create the badge for the cart menu item
        val badge = bottomNav.getOrCreateBadge(R.id.nav_cart)
        if (cartItemCount > 0) {
            badge.isVisible = true // Show the badge
            badge.number = cartItemCount // Set the count
        } else {
            badge.isVisible = false // Hide the badge if the cart is empty
        }
    }
    // ---------------------------------------------

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Order Updates"
            val descriptionText = "Notifications about your order status"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}