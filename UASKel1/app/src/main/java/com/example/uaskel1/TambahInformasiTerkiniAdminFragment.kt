package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentTambahInformasiTerkiniAdminBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahInformasiTerkiniAdminFragment : Fragment(), View.OnClickListener{
    lateinit var binding: FragmentTambahInformasiTerkiniAdminBinding
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahInformasiTerkiniAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("informasi")
        binding.btnTambahinformasi.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        simpanData()
        val informasiTerkini = InfomasiTerkiniAdminFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container_admin, informasiTerkini)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdlinformasi.text.toString().trim()
        val detail = binding.edtDtlloker.text.toString().trim()
        val tanggal = binding.edtTglupload.text.toString().trim()

        if (judul.isEmpty() || detail.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val lokerId = ref.push().key
        val loker = Loker(lokerId!!, judul, detail, tanggal)

        lokerId?.let {
            ref.child(it).setValue(loker).addOnCompleteListener { task ->
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