package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentTambahPelatihanJahitBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahPelatihanJahitFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentTambahPelatihanJahitBinding
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahPelatihanJahitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("jahit")
        binding.btnTambahpelatihanjahit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        simpanData()
        val jahit = JahitAdminFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.container_admin, jahit)
        transaction.commit()
    }

    private fun simpanData() {
        val judul = binding.edtJdlpelatihanjahit.text.toString().trim()
        val detail = binding.edtDetilpelatihanjahit.text.toString().trim()
        val tanggal = binding.edtJdlpelatihanjahit.text.toString().trim()

        if (judul.isEmpty() || detail.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val jahitId = ref.push().key
        val jahit = Jahit(jahitId!!, judul, detail, tanggal)

        jahitId?.let {
            ref.child(it).setValue(jahit).addOnCompleteListener { task ->
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