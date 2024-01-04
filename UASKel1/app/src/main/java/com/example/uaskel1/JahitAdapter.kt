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

class JahitAdapter(
    val jahitContext: Context,
    val layoutResId: Int,
    val jahitList: List<Jahit>): ArrayAdapter<Jahit>(jahitContext, layoutResId, jahitList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(jahitContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_jahit)
        val o_detil: TextView = view.findViewById(R.id.detiljahit)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_jahit)

        val jahit =jahitList[position]
        o_judul.text = jahit.judul
        o_detil.text = jahit.detail
        o_tanggal.text =jahit.tanggal

        return view
    }

}