package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentTambahPelatihanLukisBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahPelatihanLukisFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentTambahPelatihanLukisBinding
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahPelatihanLukisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("lukis")
        binding.btnTambahpelatihanlukis.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        simpanData()
        val lukis = LukisAdminFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container_admin, lukis)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdlpelatihanlukis.text.toString().trim()
        val detail = binding.edtDetilpelatihanlukis.text.toString().trim()
        val tanggal = binding.edtTgluploadlukis.text.toString().trim()

        if (judul.isEmpty() || detail.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val lukisId = ref.push().key
        val lukis = Lukis(lukisId!!, judul, detail, tanggal)

        lukisId?.let {
            ref.child(it).setValue(lukis).addOnCompleteListener { task ->
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