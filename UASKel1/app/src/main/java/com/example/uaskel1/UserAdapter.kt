package com.example.uaskel1

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserAdapter(
    val userContext: Context,
    val layoutResId: Int,
    userList: List<User>
) : ArrayAdapter<User>(userContext, layoutResId, userList.filter { it.role == "admin" }) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(userContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_nama: TextView = view.findViewById(R.id.username)
        val o_email: TextView = view.findViewById(R.id.email)

        val user = getItem(position)
        o_nama.text = user?.nama
        o_email.text = user?.email


        val auth = FirebaseAuth.getInstance()
        val hapusUser: ImageView = view.findViewById(R.id.ic_hapususer)
        hapusUser.setOnClickListener {
            val user = auth.currentUser
            user?.let {
                val userId = it.uid
                // Hapus pengguna dari Firebase Authentication
                it.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Hapus data pengguna dari Realtime Database
                        val dbAnggota = FirebaseDatabase.getInstance().getReference("user").child(userId)
                        dbAnggota.removeValue()
                        Toast.makeText(userContext, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(userContext, "Gagal menghapus akun: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                Toast.makeText(userContext, "ID pengguna tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}
