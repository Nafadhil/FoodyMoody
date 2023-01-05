package com.example.projekfoodymoody.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.retrofit.RetrofitHelper
import com.example.projekfoodymoody.api.UserApi
import com.example.projekfoodymoody.api.data.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class DaftarActivity : AppCompatActivity() {
    lateinit var btnSignUp : Button
    lateinit var etEmail : EditText
    lateinit var etPassword : EditText


    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enFveW90d3dibHd6YnRuemxkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzAwNTE3MjcsImV4cCI6MTk4NTYyNzcyN30.MwZmbsuNHEYsUeFIlGh1xE3ktEbNRAifDO_ahGfhC24"
    val token = "Bearer $apiKey"

    val todoApi = RetrofitHelper.getInstance().create(UserApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)

        btnSignUp = findViewById(R.id.btn_sign_up)
        etEmail = findViewById(R.id.et_regis_email)
        etPassword = findViewById(R.id.et_regis_password)

        btnSignUp.setOnClickListener {
            signUp(etEmail.text.toString(), etPassword.text.toString())
        }

    }

    private fun signUp(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {

            var data = Users(email = email, password = password)
            var response = todoApi.signUp(token = token, apiKey = apiKey, data = data)

            val bodyResponse = if(response.code() == 200) {
                response.body()?.string()
            } else {
                response.errorBody()?.string().toString()
            }

            var failed = false
            val jsonResponse = JSONObject(bodyResponse)
            if(jsonResponse.keys().asSequence().toList().contains("error")) {
                failed = true
            }

            var msg = ""
            if (!failed) {
                msg = "Successfully sign up!"
            } else {
                var errorMessage = jsonResponse.get("error_description")
                msg += errorMessage
            }

            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    applicationContext,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}