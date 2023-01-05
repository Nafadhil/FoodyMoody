package com.example.projekfoodymoody.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.adapter.listpesananadapter
import com.example.projekfoodymoody.retrofit.RetrofitHelper
import com.example.projekfoodymoody.api.pesananapi
import com.example.projekfoodymoody.model.ModelPesanan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KonfirmasiPesanan : AppCompatActivity() {
    lateinit var btnPesan : Button
    lateinit var listPesanan : ListView

    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJ4enFveW90d3dibHd6YnRuemxkIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NzAwNTE3MjcsImV4cCI6MTk4NTYyNzcyN30.MwZmbsuNHEYsUeFIlGh1xE3ktEbNRAifDO_ahGfhC24"
    val token = "Bearer $apiKey"

    val Items = ArrayList<ModelPesanan>()
    val todoApi = RetrofitHelper.getInstance().create(pesananapi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi_pesanan)

        listPesanan = findViewById(R.id.list_pesanan)
        btnPesan = findViewById(R.id.pesan)

        getItem()
        listPesanan.setOnItemLongClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as ModelPesanan
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Yakin hapus pesanan anda?")
                .setCancelable(false)
                .setPositiveButton("Iya") { dialog, id ->
                    val id = item.Id.toString()
                    var queryId = "eq.$id"
                    deleteItem(queryId)

                    var a = item.Makanan.toString()
                    Toast.makeText(
                        applicationContext,
                        "Berhasil menghapus pesanan $a",
                        Toast.LENGTH_SHORT
                    ).show()
                    getItem()
                }
                .setNegativeButton("Batal") { dialog, id ->
                    dialog.dismiss()
                }

            val alert = builder.create()
            alert.show()
            return@setOnItemLongClickListener true
        }
        btnPesan.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                for (i in 0 until 1000) {
                    todoApi.delete(token = token, apiKey = apiKey, idQuery = "eq.$i")
                }
            }
            val intent = Intent(this, BerandaMenu::class.java)
            startActivity(intent)
            Toast.makeText(
                applicationContext,
                "Pesanan Anda sedang diproses...",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    fun setList(Items: ArrayList<ModelPesanan>) {
        val adapter = listpesananadapter(this, R.layout.list_pesanan, Items)
        listPesanan.adapter = adapter
    }
    fun deleteItem(id: String) {
        CoroutineScope(Dispatchers.Main).launch {
            todoApi.delete(token=token, apiKey=apiKey, idQuery=id)
        }
    }
    fun getItem() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = todoApi.get(token=token, apiKey=apiKey)
            response.body()?.forEach {
                Items.add(
                    ModelPesanan(
                        Id=it.id,
                        Makanan = it.makanan,
                        Harga = it.harga,
                        Jumlah = it.jumlah,
                        Subjumlah = it.subjumlah
                    )
                )
            }
            setList(Items)
        }
    }
}