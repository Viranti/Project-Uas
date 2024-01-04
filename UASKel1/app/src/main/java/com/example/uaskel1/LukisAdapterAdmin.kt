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

class LukisAdapterAdmin (
    val lukisContext: Context,
    val layoutResId: Int,
    val lukisList: List<Lukis>): ArrayAdapter<Lukis>(lukisContext, layoutResId, lukisList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(lukisContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_lukisadm)
        val o_detil: TextView = view.findViewById(R.id.detillukisadm)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_lukisadm)
        val imgEdit: ImageView = view.findViewById(R.id.ic_editlukis)

        val lukis =lukisList[position]
        o_judul.text = lukis.judul
        o_detil.text = lukis.detail
        o_tanggal.text =lukis.tanggal

        imgEdit.setOnClickListener{
            updateDialog(lukis)
        }

        val hapusLukisImageView: ImageView = view.findViewById(R.id.ic_hapuslukis)
        hapusLukisImageView.setOnClickListener {
            val dbLukis = FirebaseDatabase.getInstance().getReference("lukis")
                .child(lukis.id)
            dbLukis.removeValue()

            Toast.makeText(lukisContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(lukis: Lukis) {
        val builder = AlertDialog.Builder(lukisContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((lukisContext))
        val view = inflater.inflate(R.layout.update_lukis, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjdlpelatihanlukis)
        val edtDetil = view.findViewById<EditText>(R.id.editdetilpelatihanlukis)
        val edtTanggal = view.findViewById<EditText>(R.id.edittgluploadlukis)

        edtJudul.setText(lukis.judul)
        edtDetil.setText(lukis.detail)
        edtTanggal.setText(lukis.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbLukis = FirebaseDatabase.getInstance().getReference("lukis")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(lukisContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val lukis = Lukis(lukis.id, judul, detil , tanggal)

            dbLukis.child(lukis.id).setValue(lukis)
            Toast.makeText(lukisContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}