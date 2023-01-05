package com.example.projekfoodymoody.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.projekfoodymoody.R
import com.example.projekfoodymoody.model.Model

class listmenuadapter (var ctx: Context, var resource: Int, var item: ArrayList<Model>): ArrayAdapter<Model>(ctx, resource, item) {
    lateinit var gambarlist: Array<String>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(ctx)
        val view = layoutInflater.inflate(resource, null)


        val nama = view.findViewById<TextView>(R.id.dimsumtxt)
        val harga = view.findViewById<TextView>(R.id.hargadimsum)
        val img = view.findViewById<ImageView>(R.id.logodimsum)
        gambarlist = view.resources.getStringArray(R.array.gambar)

        nama.text = item[position].Nama
        harga.text = item[position].Harga
        Glide.with(context)
            .load(gambarlist[position])
            .into(img);

        return view
    }
}