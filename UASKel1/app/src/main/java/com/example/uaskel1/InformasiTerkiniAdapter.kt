package com.example.uaskel1

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class InformasiTerkiniAdapter(
    val informasiContext: Context,
    val layoutResId: Int,
    val informasiList: List<InformasiTerkini>): ArrayAdapter<InformasiTerkini>(informasiContext, layoutResId, informasiList
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(informasiContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.judulinformasi)
        val o_detil: TextView = view.findViewById(R.id.detilinformasi)
        val o_tanggal: TextView = view.findViewById(R.id.tglinformasi)

        val informasi =informasiList[position]
        o_judul.text = informasi.judul
        o_detil.text = informasi.detail
        o_tanggal.text = "Di Upload : "+informasi.tanggal

        return view
    }
}