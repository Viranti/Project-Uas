package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentTambahPelatihanMeriasBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahPelatihanMeriasFragment : Fragment(), View.OnClickListener  {
    lateinit var binding: FragmentTambahPelatihanMeriasBinding
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahPelatihanMeriasBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("merias")
        binding.btnTambahpelatihanmerias.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        simpanData()
        val merias = MeriasAdminFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container_admin, merias)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdlpelatihanmerias.text.toString().trim()
        val detail = binding.edtDetilpelatihanmerias.text.toString().trim()
        val tanggal = binding.edtTgluploadmerias.text.toString().trim()

        if (judul.isEmpty() || detail.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val meriasId = ref.push().key
        val merias = Merias(meriasId!!, judul, detail, tanggal)

        meriasId?.let {
            ref.child(it).setValue(merias).addOnCompleteListener { task ->
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