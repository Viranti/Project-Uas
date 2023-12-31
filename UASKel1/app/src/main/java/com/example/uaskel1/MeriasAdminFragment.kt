package com.example.uaskel1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentMeriasAdminBinding
import com.google.firebase.database.*

class MeriasAdminFragment : Fragment() {
    lateinit var binding: FragmentMeriasAdminBinding
    private lateinit var meriasList: MutableList<Merias>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentMeriasAdminBinding.inflate(inflater, container, false)
        binding.btnPindahtambahpelatihanmerias.setOnClickListener{
            val tambahPelatihan = TambahPelatihanMeriasFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,tambahPelatihan)
            transaction.commit()
        }
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
                        val adapter = MeriasAdapterAdmin(
                            requireActivity(),
                            R.layout.item_merias_admin,
                            meriasList
                        )
                        binding.lvOutputpelatihan.adapter = adapter
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





