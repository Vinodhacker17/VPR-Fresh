package com.example.blinkitclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView // <-- Required import added

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo)
        // Corrected from ImageView to TextView
        val tagline = findViewById<TextView>(R.id.tagline)

        // Load animations
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        logo.startAnimation(slideUp)
        tagline.startAnimation(slideUp)

        // Handler to delay and start the next activity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the splash activity so the user can't go back to it
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}

