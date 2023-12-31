package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class TambahUserAdminFragment : Fragment(R.layout.fragment_tambah_user_admin) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        // Inisialisasi elemen-elemen UI
        val namaEditText: EditText = view.findViewById(R.id.edt_namaadmin)
        val emailEditText: EditText = view.findViewById(R.id.edt_emailadmin)
        val passwordEditText: EditText = view.findViewById(R.id.edt_passwordadmin)
        val btnTambah: Button = view.findViewById(R.id.btn_tambahadmin)

        // Set listener untuk tombol daftar
        btnTambah.setOnClickListener {
            val nama = namaEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validasi input sebelum pendaftaran
            if (nama.isEmpty() || email.isEmpty()  || password.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses pendaftaran ke Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Pendaftaran berhasil
                        val user = auth.currentUser
                        val userId = user?.uid

                        // Menyimpan informasi pengguna ke Realtime Database
                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("user")

                        val userKT = User(userId!!, email, nama, password, role= "admin")

                        userId?.let {
                            reference.child(it).setValue(userKT)
                                .addOnSuccessListener {
                                    // Data pengguna berhasil disimpan ke database
                                    Toast.makeText(requireContext(), "user berhasil di tambah", Toast.LENGTH_SHORT).show()
                                    val transaction :FragmentTransaction = requireFragmentManager().beginTransaction()
                                    transaction.replace(R.id.container_admin, DataUserAdminFragment())
                                    transaction.commit()

                                }
                                .addOnFailureListener { e ->
                                    // Gagal menyimpan data pengguna ke database
                                    Toast.makeText(requireContext(), "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        // Pendaftaran gagal, tampilkan pesan kesalahan
                        val exception = task.exception
                        Toast.makeText(requireContext(), "Pendaftaran gagal: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }

}