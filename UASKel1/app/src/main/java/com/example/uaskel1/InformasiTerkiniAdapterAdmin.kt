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

class InformasiTerkiniAdapterAdmin(
    val informasiContext: Context,
    val layoutResId: Int,
    val informasiList: List<InformasiTerkini>): ArrayAdapter<InformasiTerkini>(informasiContext, layoutResId, informasiList
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(informasiContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.judulinformasiadm)
        val o_detil: TextView = view.findViewById(R.id.detilinformasiadm)
        val o_tanggal: TextView = view.findViewById(R.id.tgluploadinformasiadm)
        val imgEdit: ImageView = view.findViewById(R.id.editinformasi)

        val informasi =informasiList[position]
        o_judul.text = informasi.judul
        o_detil.text = informasi.detail
        o_tanggal.text = "Di Upload : "+informasi.tanggal

        imgEdit.setOnClickListener{
            updateDialog(informasi)
        }

        val hapusInformasiTerkiniImageView: ImageView = view.findViewById(R.id.hapusinformasi)
        hapusInformasiTerkiniImageView.setOnClickListener {
            val dbAnggota = FirebaseDatabase.getInstance().getReference("informasi")
                .child(informasi.id)
            dbAnggota.removeValue()

            Toast.makeText(informasiContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(informasi: InformasiTerkini) {
        val builder = AlertDialog.Builder(informasiContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((informasiContext))
        val view = inflater.inflate(R.layout.update_informasi_terkini, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjdlinformasi)
        val edtDetil = view.findViewById<EditText>(R.id.editdtlinformasi)
        val edtTanggal = view.findViewById<EditText>(R.id.edittglupload)

        edtJudul.setText(informasi.judul)
        edtDetil.setText(informasi.detail)
        edtTanggal.setText(informasi.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbInformasiTerkini = FirebaseDatabase.getInstance().getReference("informasi")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(informasiContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val informasi = InformasiTerkini(informasi.id, judul, detil , tanggal)

            dbInformasiTerkini.child(informasi.id).setValue(informasi)
            Toast.makeText(informasiContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}