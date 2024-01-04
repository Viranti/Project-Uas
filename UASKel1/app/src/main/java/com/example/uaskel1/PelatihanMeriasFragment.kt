package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uaskel1.databinding.FragmentPelatihanMeriasBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PelatihanMeriasFragment : Fragment() {
    lateinit var binding: FragmentPelatihanMeriasBinding
    private lateinit var meriasList: MutableList<Merias>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPelatihanMeriasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("merias")
        meriasList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        meriasList.clear()
                        for (a in snapshot.children) {
                            val merias = a.getValue(Merias::class.java)
                            merias?.let {
                                meriasList.add(it)
                            }
                        }
                        val adapter = MeriasAdapter(
                            requireActivity(),
                            R.layout.item_merias,
                            meriasList
                        )
                        binding.lvPelatihanmerias.adapter = adapter
                        println("Output: " + meriasList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }


}