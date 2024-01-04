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

class LukisAdapter(
    val lukisContext: Context,
    val layoutResId: Int,
    val lukisList: List<Lukis>): ArrayAdapter<Lukis>(lukisContext, layoutResId, lukisList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lukisContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_lukis)
        val o_detil: TextView = view.findViewById(R.id.detillukis)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_lukis)

        val lukis =lukisList[position]
        o_judul.text = lukis.judul
        o_detil.text = lukis.detail
        o_tanggal.text =lukis.tanggal

        return view
    }

}