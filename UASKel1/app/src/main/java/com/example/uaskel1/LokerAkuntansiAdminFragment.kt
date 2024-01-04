package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerAkuntansiAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LokerAkuntansiAdminFragment : Fragment() {
    lateinit var binding: FragmentLokerAkuntansiAdminBinding
    private lateinit var lokerList: MutableList<LokerAkuntansi>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerAkuntansiAdminBinding.inflate(inflater, container,false)
        binding.btnTambahlokerakuntansi.setOnClickListener{
            val tambahLoker = TambahLokerAkuntansiFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin, tambahLoker)
            transaction.commit()
        }
        binding.icBack.setOnClickListener{
            val tambahLoker = LokerSpesialisAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin, tambahLoker)
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("lokerAkuntansi")
        lokerList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        lokerList.clear()
                        for (a in snapshot.children) {
                            val lokerAkt = a.getValue(LokerAkuntansi::class.java)
                            lokerAkt?.let {
                                lokerList.add(it)
                            }
                        }
                        val adapter = LokerAkuntansiAdapterAdmin(
                            requireActivity(),
                            R.layout.item_loker_akt_admin,
                            lokerList
                        )
                        binding.lvOuput.adapter = adapter
                        println("Output: " + lokerList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }


}