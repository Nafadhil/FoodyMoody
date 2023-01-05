package com.example.projekfoodymoody.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.model.ModelPesanan

class listpesananadapter (var ctx: Context, var resource: Int, var item: ArrayList<ModelPesanan>): ArrayAdapter<ModelPesanan>(ctx, resource, item) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(resource, null)


        val nama = view.findViewById<TextView>(R.id.namapesanan)
        val harga = view.findViewById<TextView>(R.id.hargapesanan)
        val jumlah = view.findViewById<TextView>(R.id.jumlahpesanan)

        nama.text = item[position].Makanan
        harga.text = item[position].Harga
        jumlah.text = item[position].Jumlah

        return view
    }
}