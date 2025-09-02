package com.example.blinkitclone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo: ImageView = findViewById(R.id.logo)
        val tagline: TextView = findViewById(R.id.tagline)

        // Load animation
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_fade_in)
        logo.startAnimation(animation)
        tagline.startAnimation(animation)

        // Delay and start MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000) // 2 second delay
    }
}
