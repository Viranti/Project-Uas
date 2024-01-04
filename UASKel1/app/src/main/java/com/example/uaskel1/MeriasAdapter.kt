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

class MeriasAdapter(
    val meriasContext: Context,
    val layoutResId: Int,
    val meriasList: List<Merias>): ArrayAdapter<Merias>(meriasContext, layoutResId, meriasList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(meriasContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_merias)
        val o_detil: TextView = view.findViewById(R.id.detilmerias)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_merias)

        val merias =meriasList[position]
        o_judul.text = merias.judul
        o_detil.text = merias.detail
        o_tanggal.text =merias.tanggal

        return view
    }
}