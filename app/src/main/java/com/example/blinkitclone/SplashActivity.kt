package com.example.blinkitclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance() // Initialize Firebase Auth

        // Your existing animation code is kept intact
        val logo = findViewById<ImageView>(R.id.logo)
        val tagline = findViewById<TextView>(R.id.tagline)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        logo.startAnimation(slideUp)
        tagline.startAnimation(slideUp)

        // Handler to delay and start the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            // --- THIS IS THE UPDATED LOGIC ---
            // Check if a user is already logged in
            if (auth.currentUser != null) {
                // If user is logged in, go directly to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // If no user is logged in, go to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish() // Finish SplashActivity so user can't go back to it
            // ---------------------------------
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}