package com.example.uaskel1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahLokerTeknologiFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var edtJudul: EditText
    private lateinit var edtDetail: EditText
    private lateinit var edtTanggal: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tambah_loker_teknologi, container, false)

        // Inisialisasi Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("loker")

        // Inisialisasi views
        edtJudul = view.findViewById(R.id.edt_dltloker)
        edtDetail = view.findViewById(R.id.edt_dtlloker)
        edtTanggal = view.findViewById(R.id.edt_tgloker)
        val btnTambahLoker: Button = view.findViewById(R.id.btn_tambahloker)

        btnTambahLoker.setOnClickListener {
            tambahDataLoker()
        }

        return view
    }

    private fun tambahDataLoker() {
        val judul = edtJudul.text.toString().trim()
        val detail = edtDetail.text.toString().trim()
        val tanggal = edtTanggal.text.toString().trim()

        if (judul.isNotEmpty() && detail.isNotEmpty() && tanggal.isNotEmpty()) {
            val idLoker = databaseReference.push().key ?: return

            // Membuat objek Loker
            val loker = Loker(idLoker, judul, detail, tanggal)

            // Menambahkan data ke Firebase Realtime Database
            databaseReference.child(idLoker).setValue(loker)
                .addOnSuccessListener {
                    // Data berhasil ditambahkan
                    Toast.makeText(requireContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()

                    // Lakukan sesuatu setelah data ditambahkan, misalnya, bersihkan field input
                    edtJudul.text.clear()
                    edtDetail.text.clear()
                    edtTanggal.text.clear()
                }
                .addOnFailureListener {
                    // Gagal menambahkan data
                    Toast.makeText(requireContext(), "Gagal menambahkan data: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Harap isi semua field", Toast.LENGTH_SHORT).show()
        }
    }
}
