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

class JahitAdapterAdmin (
    val jahitContext: Context,
    val layoutResId: Int,
    val jahitList: List<Jahit>): ArrayAdapter<Jahit>(jahitContext, layoutResId, jahitList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(jahitContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_judul: TextView = view.findViewById(R.id.jdl_jahitadm)
        val o_detil: TextView = view.findViewById(R.id.detiljahitadm)
        val o_tanggal: TextView = view.findViewById(R.id.tgl_jahitadm)
        val imgEdit: ImageView = view.findViewById(R.id.ic_editjahit)

        val jahit =jahitList[position]
        o_judul.text = jahit.judul
        o_detil.text = jahit.detail
        o_tanggal.text =jahit.tanggal

        imgEdit.setOnClickListener{
            updateDialog(jahit)
        }

        val hapusJahitImageView: ImageView = view.findViewById(R.id.ic_hapusjahit)
        hapusJahitImageView.setOnClickListener {
            val dbJahit = FirebaseDatabase.getInstance().getReference("jahit")
                .child(jahit.id)
            dbJahit.removeValue()

            Toast.makeText(jahitContext,"Data berhasil di hapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(jahit: Jahit) {
        val builder = AlertDialog.Builder(jahitContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from((jahitContext))
        val view = inflater.inflate(R.layout.update_pelatihan_jahit, null)

        val edtJudul = view.findViewById<EditText>(R.id.editjdlpelatihanjahit)
        val edtDetil = view.findViewById<EditText>(R.id.editdetilpelatihanjahit)
        val edtTanggal = view.findViewById<EditText>(R.id.edittgluploadjahit)

        edtJudul.setText(jahit.judul)
        edtDetil.setText(jahit.detail)
        edtTanggal.setText(jahit.tanggal)

        builder.setView(view)

        builder.setPositiveButton("Edit"){p0,p1 ->
            val dbJahit = FirebaseDatabase.getInstance().getReference("jahit")
            val judul = edtJudul.text.toString().trim()
            val detil = edtDetil.text.toString().trim()
            val tanggal = edtTanggal.text.toString().trim()

            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
                Toast.makeText(jahitContext,"Isi data secara lengkap tidak boleh kosong",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setPositiveButton
            }

            val jahit = Jahit(jahit.id, judul, detil , tanggal)

            dbJahit.child(jahit.id).setValue(jahit)
            Toast.makeText(jahitContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNeutralButton("Batal"){p0,p1 ->}
        val alerts = builder.create()
        alerts.show()
    }
}