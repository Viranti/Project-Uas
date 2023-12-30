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
import com.google.firebase.database.FirebaseDatabase

class UserAdapter
    (
    val userContext: Context,
    val layoutResId: Int,
    val userList: List<User>): ArrayAdapter<User>(userContext, layoutResId, userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(userContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val o_nama: TextView = view.findViewById(R.id.username)
        val o_email: TextView = view.findViewById(R.id.email)

        val user =userList[position]
        o_nama.text = user.nama
        o_email.text = user.email

        return view
    }

//    private fun updateDialog(user: User) {
//        val builder = AlertDialog.Builder(userContext)
//        builder.setTitle("Update Data")
//        val inflater = LayoutInflater.from((userContext))
//        val view = inflater.inflate(R.layout.update_user_teknologi, null)
//
//        val edtJudul = view.findViewById<EditText>(R.id.editjuduluser)
//        val edtDetil = view.findViewById<EditText>(R.id.editdetiluser)
//        val edtTanggal = view.findViewById<EditText>(R.id.edittgluser)
//
//        edtJudul.setText(user.judul)
//        edtDetil.setText(user.detail)
//        edtTanggal.setText(user.tanggal)
//
//        builder.setView(view)
//
//        builder.setPositiveButton("Edit"){p0,p1 ->
//            val dbUser = FirebaseDatabase.getInstance().getReference("user")
//            val judul = edtJudul.text.toString().trim()
//            val detil = edtDetil.text.toString().trim()
//            val tanggal = edtTanggal.text.toString().trim()
//
//            if (judul.isEmpty() or detil.isEmpty() or tanggal.isEmpty()){
//                Toast.makeText(userContext,"Isi data secara lengkap tidak boleh kosong",
//                    Toast.LENGTH_SHORT)
//                    .show()
//                return@setPositiveButton
//            }
//
//            val user = User(user.id, judul, detil , tanggal)
//
//            dbUser.child(user.id).setValue(user)
//            Toast.makeText(userContext,"Data Berhasil di update", Toast.LENGTH_SHORT)
//                .show()
//        }
//        builder.setNeutralButton("Batal"){p0,p1 ->}
//        val alerts = builder.create()
//        alerts.show()
//    }
}