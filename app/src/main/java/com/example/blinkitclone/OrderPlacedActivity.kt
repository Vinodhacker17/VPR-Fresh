package com.example.blinkitclone

import android.content.Intent
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blinkitclone.databinding.ActivityOrderPlacedBinding

class OrderPlacedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderPlacedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPlacedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- THIS IS THE CRUCIAL LOGIC ---
        // It gets the drawable from the ImageView and starts the animationH
        val animatable = binding.successAnimationView.drawable as Animatable
        animatable.start()
        // ---------------------------------

        binding.backToHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}