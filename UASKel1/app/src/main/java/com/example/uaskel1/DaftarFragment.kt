package com.example.uaskel1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DaftarFragment : Fragment(R.layout.fragment_daftar) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        // Inisialisasi elemen-elemen UI
        val namaEditText: EditText = view.findViewById(R.id.edt_nama)
        val emailEditText: EditText = view.findViewById(R.id.edt_email)
        val passwordEditText: EditText = view.findViewById(R.id.edt_password)
        val btnDaftar: Button = view.findViewById(R.id.btn_daftar)
        val textMasuk: TextView = view.findViewById(R.id.tv_masuk)

        // Set listener untuk tombol daftar
        btnDaftar.setOnClickListener {
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

                        val userData = HashMap<String, Any>()
                        userData["email"] = email
                        userData["nama"] = nama
                        userData["password"] = password
                        userData["role"] = "user"

                        userId?.let {
                            reference.child(it).setValue(userData)
                                .addOnSuccessListener {
                                    // Data pengguna berhasil disimpan ke database
                                    Toast.makeText(requireContext(), "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
                                    // Redirect ke halaman utama setelah pendaftaran berhasil
                                    // Redirect ke halaman utama setelah pendaftaran berhasil
                                    val masukFragment = MasukFragment()
                                    val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()

                                    if (!requireActivity().isFinishing && !requireActivity().isDestroyed) {
                                        transaction.replace(R.id.splash, masukFragment)
                                        transaction.commit()
                                    }

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
        textMasuk.setOnClickListener {
            val masuk = MasukFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.splash,masuk)
            transaction.commit()
        }

    }
}