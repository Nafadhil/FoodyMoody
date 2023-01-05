package com.example.projekfoodymoody.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.retrofit.RetrofitHelper
import com.example.projekfoodymoody.api.UserApi
import com.example.projekfoodymoody.api.data.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var btnSignIn: Button
    lateinit var btnSignUp: TextView
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enFveW90d3dibHd6YnRuemxkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzAwNTE3MjcsImV4cCI6MTk4NTYyNzcyN30.MwZmbsuNHEYsUeFIlGh1xE3ktEbNRAifDO_ahGfhC24"
    val token = "Bearer $apiKey"

    val todoApi = RetrofitHelper.getInstance().create(UserApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnSignIn = findViewById(R.id.signin)
        btnSignUp = findViewById(R.id.signup)
        etEmail = findViewById(R.id.email)
        etPassword = findViewById(R.id.pw)

        btnSignIn.setOnClickListener {
            signIn(etEmail.text.toString(), etPassword.text.toString())
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, DaftarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val data = Users(email = email, password = password)
            val response = todoApi.signIn(token = token, apiKey = apiKey, data = data)

            val bodyResponse = if (response.code() == 200) {
                response.body()?.string()
            } else {
                response.errorBody()?.string().toString()
            }

            var failed = false
            val jsonResponse = JSONObject(bodyResponse)
            if (jsonResponse.keys().asSequence().toList().contains("error")) {
                failed = true
            }

            var msg = ""
            if (!failed) {
                var email = jsonResponse.getJSONObject("user").get("email").toString()
                msg = "Successfully login! Welcome back: $email"


                val sharedPreference = getSharedPreferences(
                    "app_preference", Context.MODE_PRIVATE
                )

                var editor = sharedPreference.edit()
                editor.putString("email", email)
                editor.commit()

            } else {
                msg += jsonResponse.get("error_description").toString()
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()

                if (!failed) {
                    goToHome();
                }
            }
        }
    }

    private fun goToHome() {
        val intent = Intent(this, BerandaMenu::class.java)
        startActivity(intent)
        finish()
    }
}