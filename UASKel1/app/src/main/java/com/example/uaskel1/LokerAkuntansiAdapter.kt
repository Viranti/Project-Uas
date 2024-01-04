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

class LokerAkuntansiAdapter(
    val lokerContext: Context,
    val layoutResId: Int,
    val lokerList: List<LokerAkuntansi>): ArrayAdapter<LokerAkuntansi>(lokerContext, layoutResId, lokerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lokerContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.itmjdllokerakt)
        val o_detil: TextView = view.findViewById(R.id.itmdetillokerakt)
        val o_tanggal: TextView = view.findViewById(R.id.itmtgllokerakt)

        val lokerAkt =lokerList[position]
        o_judul.text = lokerAkt.judul
        o_detil.text = lokerAkt.detail
        o_tanggal.text = "Pentupun Pendaftaran : "+lokerAkt.tanggal

        return view
    }

}