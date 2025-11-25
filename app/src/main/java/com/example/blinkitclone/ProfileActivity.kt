package com.example.blinkitclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val logoutButton = findViewById<TextView>(R.id.logout_button)
        val userPhoneText = findViewById<TextView>(R.id.user_phone)
        val userNameText = findViewById<TextView>(R.id.user_name)

        // Optional: Display the current user's email/phone
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userPhoneText.text = currentUser.email ?: currentUser.phoneNumber
            userNameText.text = currentUser.displayName ?: "Blinkit User"
        }

        // Handle the toolbar's back button
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Handle the logout button click
        logoutButton.setOnClickListener {
            // 1. SIGN OUT FROM FIREBASE (Crucial Step)
            auth.signOut()

            // 2. Clear the local cart data so the next user starts fresh
            Cart.clearCart()

            // 3. Navigate back to Login Activity
            val intent = Intent(this, LoginActivity::class.java)
            // Clear the back stack so the user can't press "Back" to return
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}