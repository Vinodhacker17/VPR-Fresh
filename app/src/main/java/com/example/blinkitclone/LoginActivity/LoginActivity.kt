package com.example.blinkitclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val continueButton = findViewById<MaterialButton>(R.id.continue_button)

        continueButton.setOnClickListener {
            // For now, just go to the main activity.
            // Later, you would add phone authentication logic here.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish LoginActivity so user can't go back
        }
    }
}