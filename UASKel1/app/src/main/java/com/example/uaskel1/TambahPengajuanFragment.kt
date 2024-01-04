package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DatabaseReference
import com.example.uaskel1.databinding.FragmentTambahPengajuanBinding
import com.google.firebase.database.FirebaseDatabase

class TambahPengajuanFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentTambahPengajuanBinding
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahPengajuanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("pengajuan")
        binding.btnTambahpengajuan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        simpanData()
        val pengajuan = PengajuanFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container, pengajuan)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdlpengajuan.text.toString().trim()
        val detail = binding.edtDtlpengajuan.text.toString().trim()
        val tanggal = binding.edtTglpengajuan.text.toString().trim()

        if (judul.isEmpty() || detail.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val pengajuanId = ref.push().key
        val pengajuan = Pengajuan(pengajuanId!!, judul, detail, tanggal)

        pengajuanId?.let {
            ref.child(it).setValue(pengajuan).addOnCompleteListener { task ->
                if(isAdded) {
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Gagal menambahkan data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}