package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uaskel1.databinding.FragmentPengajuanAdminBinding
import com.example.uaskel1.databinding.FragmentPengajuanBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PengajuanAdminFragment : Fragment() {
    lateinit var binding: FragmentPengajuanAdminBinding
    private lateinit var ref: DatabaseReference
    private lateinit var pengajuanList: MutableList<Pengajuan>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPengajuanAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("pengajuan")
        pengajuanList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        pengajuanList.clear()
                        for (a in snapshot.children) {
                            val penganjuan = a.getValue(Pengajuan::class.java)
                            penganjuan?.let {
                                pengajuanList.add(it)
                            }
                        }
                        val adapter = PengajuanAdapter(
                            requireActivity(),
                            R.layout.item_pengajuan_admin,
                            pengajuanList
                        )
                        binding.lvOutputpengajuanadmin.adapter = adapter
                        println("Output: " + pengajuanList)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}