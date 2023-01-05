package com.example.projekfoodymoody.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.projekfoodymoody.R
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {
    lateinit var txtMessageLoading : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        txtMessageLoading = findViewById(R.id.txt_message_loading)
        txtMessageLoading.text = "Initializing your app for the first time \uD83D\uDE09"
        val sharedPreference =  getSharedPreferences(
            "app_preference", Context.MODE_PRIVATE
        )

        var email = sharedPreference.getString("email", "").toString()
        if (email.isNotEmpty()) {
            txtMessageLoading.text = "Hey, how are you? $email \uD83D\uDE09"
        }


        Timer().schedule(3000) {
            if (email.isNotEmpty()) {
                goToHomePage()
            } else {
                goToSignInPage()
            }
        }
    }

    private fun goToSignInPage() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun goToHomePage() {
        val intent = Intent(this, BerandaMenu::class.java)
        startActivity(intent)
        finish()
    }
}