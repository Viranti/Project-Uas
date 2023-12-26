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

class LokerAdapter(
    val lokerContext: Context,
    val layoutResId: Int,
    val lokerList: List<Loker>): ArrayAdapter<Loker>(lokerContext, layoutResId, lokerList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lokerContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.lkJudul)
        val o_detil: TextView = view.findViewById(R.id.lkDetail)
        val o_tanggal: TextView = view.findViewById(R.id.lkTanggal)
        val imgEdit: ImageView = view.findViewById(R.id.editloker)

        val loker =lokerList[position]
        o_judul.text = loker.judul
        o_detil.text = loker.detail
        o_tanggal.text = "Pentupun Pendaftaran : "+loker.tanggal

        imgEdit.setOnClickListener{
            updateDialog(loker)
        }

        val hapusLokerImageView: ImageView = view.findViewById(R.id.hapusloker)
        hapusLokerImageView.setOnClickListener {
            val dbAnggota = FirebaseDatabase.getInstance().getReference("loker")
                .child(loker.id)
            dbAnggota.removeValue()

            Toast.makeText(lokerContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(loker: Loker) {
        val builder = AlertDialog.Builder(lokerContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((lokerContext))
        val view = inflater.inflate(R.layout.update_loker_teknologi, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjudulloker)
        val edtDetil = view.findViewById<EditText>(R.id.editdetilloker)
        val edtTanggal = view.findViewById<EditText>(R.id.edittglloker)

        edtJudul.setText(loker.judul)
        edtDetil.setText(loker.detail)
        edtTanggal.setText(loker.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbLoker = FirebaseDatabase.getInstance().getReference("loker")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(lokerContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val loker = Loker(loker.id, judul, detil , tanggal)

            dbLoker.child(loker.id).setValue(loker)
            Toast.makeText(lokerContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}