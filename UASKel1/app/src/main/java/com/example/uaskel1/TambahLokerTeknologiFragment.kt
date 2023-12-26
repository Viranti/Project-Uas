package com.example.uaskel1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.uaskel1.databinding.FragmentTambahLokerTeknologiBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TambahLokerTeknologiFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentTambahLokerTeknologiBinding
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahLokerTeknologiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("loker")
        binding.btnTambahloker.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        simpanData()
        val lokerTeknologi = LokerTeknologiFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container_admin, lokerTeknologi)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdlloker.text.toString().trim()
        val detail = binding.edtDtlloker.text.toString().trim()
        val tanggal = binding.edtTgloker.text.toString().trim()

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

