package com.example.uaskel1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerTeknologiAdminBinding
import com.google.firebase.database.*

class LokerTeknologiAdminFragment : Fragment() {
    private lateinit var binding: FragmentLokerTeknologiAdminBinding
    private lateinit var lokerList: MutableList<LokerIT>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerTeknologiAdminBinding.inflate(inflater, container, false)
        binding.btnTambahloker.setOnClickListener{
            val tambahLoker = TambahLokerTeknologiFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin, tambahLoker)
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("loker")
        lokerList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        lokerList.clear()
                        for (a in snapshot.children) {
                            val lokerIT = a.getValue(LokerIT::class.java)
                            lokerIT?.let {
                                lokerList.add(it)
                            }
                        }
                        val adapter = LokerITAdapterAdmin(
                            requireActivity(),
                            R.layout.item_loker_admin,
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

