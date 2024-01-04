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

class LokerPendidikanAdapterAdmin (
    val lokerContext: Context,
    val layoutResId: Int,
    val lokerList: List<LokerPendidikan>): ArrayAdapter<LokerPendidikan>(lokerContext, layoutResId, lokerList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lokerContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.lkPdkJudul)
        val o_detil: TextView = view.findViewById(R.id.lkPdkDetail)
        val o_tanggal: TextView = view.findViewById(R.id.lkPdkTanggal)
        val imgEdit: ImageView = view.findViewById(R.id.editlokerPdk)

        val lokerPdk =lokerList[position]
        o_judul.text = lokerPdk.judul
        o_detil.text = lokerPdk.detail
        o_tanggal.text = "Pentupun Pendaftaran : "+lokerPdk.tanggal

        imgEdit.setOnClickListener{
            updateDialog(lokerPdk)
        }

        val hapusLokerImageView: ImageView = view.findViewById(R.id.hapuslokerPdk)
        hapusLokerImageView.setOnClickListener {
            val dbLokerPendidikan = FirebaseDatabase.getInstance().getReference("lokerPendidikan")
                .child(lokerPdk.id)
            dbLokerPendidikan.removeValue()

            Toast.makeText(lokerContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(loker: LokerPendidikan) {
        val builder = AlertDialog.Builder(lokerContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((lokerContext))
        val view = inflater.inflate(R.layout.update_loker_pendidikan, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjudullokerpdk)
        val edtDetil = view.findViewById<EditText>(R.id.editdetillokerpdk)
        val edtTanggal = view.findViewById<EditText>(R.id.edittgllokerpdk)

        edtJudul.setText(loker.judul)
        edtDetil.setText(loker.detail)
        edtTanggal.setText(loker.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbLoker = FirebaseDatabase.getInstance().getReference("lokerPendidikan")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(lokerContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val loker = LokerPendidikan(loker.id, judul, detil , tanggal)

            dbLoker.child(loker.id).setValue(loker)
            Toast.makeText(lokerContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}