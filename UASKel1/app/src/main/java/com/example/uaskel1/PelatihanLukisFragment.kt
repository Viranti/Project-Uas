package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uaskel1.databinding.FragmentPelatihanLukisBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PelatihanLukisFragment : Fragment() {
    lateinit var binding: FragmentPelatihanLukisBinding
    private lateinit var lukisList: MutableList<Lukis>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPelatihanLukisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("lukis")
        lukisList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        lukisList.clear()
                        for (a in snapshot.children) {
                            val lukis = a.getValue(Lukis::class.java)
                            lukis?.let {
                                lukisList.add(it)
                            }
                        }
                        val adapter = LukisAdapter(
                            requireActivity(),
                            R.layout.item_lukis,
                            lukisList
                        )
                        binding.lvPelatihanlukis.adapter = adapter
                        println("Output: " + lukisList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

}