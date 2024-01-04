package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerPendidikanAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LokerPendidikanAdminFragment : Fragment() {
    lateinit var binding: FragmentLokerPendidikanAdminBinding
    private lateinit var lokerList: MutableList<LokerPendidikan>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerPendidikanAdminBinding.inflate(inflater, container, false)
        binding.btnTambahlokerpendidikan.setOnClickListener{
            val tambahLoker = TambahLokerPendidikanFragment()
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

        ref = FirebaseDatabase.getInstance().getReference("lokerPendidikan")
        lokerList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        lokerList.clear()
                        for (a in snapshot.children) {
                            val lokerAkt = a.getValue(LokerPendidikan::class.java)
                            lokerAkt?.let {
                                lokerList.add(it)
                            }
                        }
                        val adapter = LokerPendidikanAdapterAdmin(
                            requireActivity(),
                            R.layout.item_loker_pdk_admin,
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