package com.example.uaskel1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class LokerITAdapter(
    val lokerContext: Context,
    val layoutResId: Int,
    val lokerList: List<Loker>): ArrayAdapter<Loker>(lokerContext, layoutResId, lokerList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lokerContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.itmjdllokerit)
        val o_detil: TextView = view.findViewById(R.id.itmdetillokerit)
        val o_tanggal: TextView = view.findViewById(R.id.itmtgllokerit)

        val loker =lokerList[position]
        o_judul.text = loker.judul
        o_detil.text = loker.detail
        o_tanggal.text = "Pentupun Pendaftaran : "+loker.tanggal

        return view
    }
}