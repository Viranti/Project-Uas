package com.example.uaskel1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerTeknologiBinding
import com.google.firebase.database.*

class LokerTeknologiFragment : Fragment() {
    private lateinit var lokerList: MutableList<Loker>
    private lateinit var binding: FragmentLokerTeknologiBinding
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerTeknologiBinding.inflate(inflater, container, false)
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
                            val anggota = a.getValue(Loker::class.java)
                            anggota?.let {
                                lokerList.add(it)
                            }
                        }
                        val adapter = LokerAdapter(
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

