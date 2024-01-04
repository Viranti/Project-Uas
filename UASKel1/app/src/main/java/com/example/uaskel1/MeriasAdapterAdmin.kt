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

class MeriasAdapterAdmin (
    val meriasContext: Context,
    val layoutResId: Int,
    val meriasList: List<Merias>): ArrayAdapter<Merias>(meriasContext, layoutResId, meriasList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(meriasContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_meriasadm)
        val o_detil: TextView = view.findViewById(R.id.detilmeriasadm)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_meriasadm)
        val imgEdit: ImageView = view.findViewById(R.id.ic_editmerias)

        val merias =meriasList[position]
        o_judul.text = merias.judul
        o_detil.text = merias.detail
        o_tanggal.text =merias.tanggal

        imgEdit.setOnClickListener{
            updateDialog(merias)
        }

        val hapusLokerImageView: ImageView = view.findViewById(R.id.ic_hapusmerias)
        hapusLokerImageView.setOnClickListener {
            val dbMerias = FirebaseDatabase.getInstance().getReference("merias")
                .child(merias.id)
            dbMerias.removeValue()

            Toast.makeText(meriasContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(merias: Merias) {
        val builder = AlertDialog.Builder(meriasContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((meriasContext))
        val view = inflater.inflate(R.layout.update_merias, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjudulmerias)
        val edtDetil = view.findViewById<EditText>(R.id.editdetilpelatihanmerias)
        val edtTanggal = view.findViewById<EditText>(R.id.edittgluploadmerias)

        edtJudul.setText(merias.judul)
        edtDetil.setText(merias.detail)
        edtTanggal.setText(merias.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbMerias = FirebaseDatabase.getInstance().getReference("merias")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(meriasContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val merias = Merias(merias.id, judul, detil , tanggal)

            dbMerias.child(merias.id).setValue(merias)
            Toast.makeText(meriasContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}