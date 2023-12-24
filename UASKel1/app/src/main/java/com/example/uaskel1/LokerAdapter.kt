package com.example.uaskel1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class LokerAdapter(
    val lokerContext: Context,
    val layoutResId: Int,
    val lokerList: List<Loker>): ArrayAdapter<Loker>(lokerContext, layoutResId, lokerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lokerContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.ou_jdlloker)
        val o_detil: TextView = view.findViewById(R.id.ou_dtlloker)
        val o_tanggal: TextView = view.findViewById(R.id.ou_tglloker)

        val loker =lokerList[position]

        o_judul.text = "Judul : "+loker.judul
        o_detil.text = "Detil : "+loker.detail
        o_tanggal.text = "Pentupun Pendaftaran : "+loker.tanggal

        return view
    }
}