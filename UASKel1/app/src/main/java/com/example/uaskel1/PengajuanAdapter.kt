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

class PengajuanAdapter(
    val pengajuanContext: Context,
    val layoutResId: Int,
    val pengajuanList: List<Pengajuan>): ArrayAdapter<Pengajuan>(pengajuanContext, layoutResId, pengajuanList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(pengajuanContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_pengajuanitm)
        val o_detil: TextView = view.findViewById(R.id.detil_pengajuanitm)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_pengajuanitm)
        val imgEdit: ImageView = view.findViewById(R.id.ic_editpengajuan)

        val pengajuan =pengajuanList[position]
        o_judul.text = pengajuan.judul
        o_detil.text = pengajuan.detail
        o_tanggal.text = pengajuan.tanggal

        imgEdit.setOnClickListener{
            updateDialog(pengajuan)
        }

        val hapusPengajuanImageView: ImageView = view.findViewById(R.id.ic_hapuspengajuan)
        hapusPengajuanImageView.setOnClickListener {
            val dbAnggota = FirebaseDatabase.getInstance().getReference("pengajuan")
                .child(pengajuan.id)
            dbAnggota.removeValue()

            Toast.makeText(pengajuanContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(pengajuan: Pengajuan) {
        val builder = AlertDialog.Builder(pengajuanContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((pengajuanContext))
        val view = inflater.inflate(R.layout.update_pengajuan, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjudulpengajuan)
        val edtDetil = view.findViewById<EditText>(R.id.editdtlpengajuan)
        val edtTanggal = view.findViewById<EditText>(R.id.edittglpengajuan)

        edtJudul.setText(pengajuan.judul)
        edtDetil.setText(pengajuan.detail)
        edtTanggal.setText(pengajuan.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbPengajuan = FirebaseDatabase.getInstance().getReference("pengajuan")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(pengajuanContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val pengajuan = Pengajuan(pengajuan.id, judul, detil , tanggal)

            dbPengajuan.child(pengajuan.id).setValue(pengajuan)
            Toast.makeText(pengajuanContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}