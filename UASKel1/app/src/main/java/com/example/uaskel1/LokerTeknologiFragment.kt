package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uaskel1.databinding.FragmentLokerTeknologiBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LokerTeknologiFragment : Fragment() {
    lateinit var binding: FragmentLokerTeknologiBinding
    private lateinit var lokerList: MutableList<LokerIT>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerTeknologiBinding.inflate(inflater, container, false)
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
                        val adapter = LokerITAdapter(
                            requireActivity(),
                            R.layout.item_loker_teknologi,
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