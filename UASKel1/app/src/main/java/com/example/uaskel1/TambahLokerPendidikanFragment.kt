package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentTambahLokerPendidikanBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahLokerPendidikanFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentTambahLokerPendidikanBinding
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahLokerPendidikanBinding.inflate(inflater, container, false)
        binding.icBack.setOnClickListener{
            val tambahLoker = LokerPendidikanAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin, tambahLoker)
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("lokerPendidikan")
        binding.btnTambahlokerpdk.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        simpanData()
        val lokerPendidikan = LokerPendidikanAdminFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container_admin, lokerPendidikan)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdllokerpdk.text.toString().trim()
        val detail = binding.edtDtllokerpdk.text.toString().trim()
        val tanggal = binding.edtTglokerpdk.text.toString().trim()

        if (judul.isEmpty() || detail.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val lokerAktId = ref.push().key
        val lokerAkt = LokerIT(lokerAktId!!, judul, detail, tanggal)

        lokerAktId?.let {
            ref.child(it).setValue(lokerAkt).addOnCompleteListener { task ->
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