package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.uaskel1.databinding.FragmentLukisAdminBinding
import com.google.firebase.database.DatabaseReference

class LukisAdminFragment : Fragment() {
    lateinit var binding: FragmentLukisAdminBinding
    private lateinit var lukisList: MutableList<Lukis>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLukisAdminBinding.inflate(inflater,container, false)
        binding.btnPindahtambahpelatihanlukis.setOnClickListener{
            val tambahPelatihan = TambahPelatihanLukisFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,tambahPelatihan)
            transaction.commit()
        }
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
                        val adapter = LukisAdapterAdmin(
                            requireActivity(),
                            R.layout.item_lukis_admin,
                            lukisList
                        )
                        binding.lvOutputpelatihan.adapter = adapter
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