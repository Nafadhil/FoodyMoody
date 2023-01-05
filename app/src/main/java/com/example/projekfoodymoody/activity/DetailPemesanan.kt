package com.example.projekfoodymoody.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.retrofit.RetrofitHelper
import com.example.projekfoodymoody.api.data.pesanandata
import com.example.projekfoodymoody.api.pesananapi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailPemesanan : AppCompatActivity() {
    lateinit var btnBeli: Button
    lateinit var etmakanan : EditText
    lateinit var etharga : EditText
    lateinit var etjumlah : EditText
    lateinit var id : String

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enFveW90d3dibHd6YnRuemxkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzAwNTE3MjcsImV4cCI6MTk4NTYyNzcyN30.MwZmbsuNHEYsUeFIlGh1xE3ktEbNRAifDO_ahGfhC24"
    val token = "Bearer $apiKey"

    val todoApi = RetrofitHelper.getInstance().create(pesananapi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pemesanan)

        etmakanan = findViewById(R.id.etmakanan)
        etharga = findViewById(R.id.etharga)
        etjumlah = findViewById(R.id.jumlah)
        btnBeli = findViewById(R.id.btn)

        id = intent.getStringExtra("id").toString()
        etmakanan.setText(intent.getStringExtra("title").toString())
        etharga.setText(intent.getStringExtra("description").toString())

        btnBeli.setOnClickListener {
            var total = 0
            total = etharga.text.toString().trim().toInt() * etjumlah.text.toString().trim().toInt()
            CoroutineScope(Dispatchers.Main).launch {

                val data = pesanandata(
                    makanan = etmakanan.text.toString(),
                    harga = etharga.text.toString(),
                    jumlah = etjumlah.text.toString(),
                    subjumlah = total.toString()
                )
                val response = todoApi.create(token = token, apiKey = apiKey, todoData = data)
                finish()
            }
            val intent = Intent(this, BerandaMenu::class.java)
            startActivity(intent)
            Toast.makeText(
                applicationContext,
                "Pesanan telah dimasukan ke keranjang",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}