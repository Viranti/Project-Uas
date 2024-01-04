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

class LokerPendidikanAdapter(
    val lokerContext: Context,
    val layoutResId: Int,
    val lokerList: List<LokerPendidikan>): ArrayAdapter<LokerPendidikan>(lokerContext, layoutResId, lokerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lokerContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.itmjdllokerpdk)
        val o_detil: TextView = view.findViewById(R.id.itmdetillokerpdk)
        val o_tanggal: TextView = view.findViewById(R.id.itmtgllokerpdk)

        val lokerPdk =lokerList[position]
        o_judul.text = lokerPdk.judul
        o_detil.text = lokerPdk.detail
        o_tanggal.text = "Pentupun Pendaftaran : "+lokerPdk.tanggal

        return view
    }
}