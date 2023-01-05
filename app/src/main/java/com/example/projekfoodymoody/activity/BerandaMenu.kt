package com.example.projekfoodymoody.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.adapter.listmenuadapter
import com.example.projekfoodymoody.retrofit.RetrofitHelper
import com.example.projekfoodymoody.api.menuapi
import com.example.projekfoodymoody.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BerandaMenu : AppCompatActivity() {
    lateinit var listMenu : ListView
    lateinit var btnKeranjang : ImageButton
    lateinit var btnLogo : ImageButton

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enFveW90d3dibHd6YnRuemxkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzAwNTE3MjcsImV4cCI6MTk4NTYyNzcyN30.MwZmbsuNHEYsUeFIlGh1xE3ktEbNRAifDO_ahGfhC24"
    val token = "Bearer $apiKey"

    val Items = ArrayList<Model>()
    val todoApi = RetrofitHelper.getInstance().create(menuapi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda_menu)

        listMenu = findViewById(R.id.listmenu)
        btnKeranjang = findViewById(R.id.keranjang)
        btnLogo = findViewById(R.id.logoapp)

        CoroutineScope(Dispatchers.Main).launch {
            val response = todoApi.get(token=token, apiKey=apiKey)

            response.body()?.forEach {
                Items.add(
                    Model(
                        Id=it.id,
                        Nama = it.nama,
                        Harga = it.harga
                    )
                )
            }
            setList(Items)
        }

        listMenu.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Model
            val intent = Intent(this, DetailPemesanan::class.java)
            intent.putExtra("id", item.Id)
            intent.putExtra("title", item.Nama)
            intent.putExtra("description", item.Harga)
            startActivity(intent)
        }

        btnKeranjang.setOnClickListener {
            val intent = Intent(this, KonfirmasiPesanan::class.java)
            startActivity(intent)
        }

        btnLogo.setOnClickListener{
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }

    fun setList(Items: ArrayList<Model>) {
        val adapter = listmenuadapter(this, R.layout.list_menu, Items)
        listMenu.adapter = adapter

    }
}