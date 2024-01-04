package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerPendidikanBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LokerPendidikanFragment : Fragment() {
    lateinit var binding: FragmentLokerPendidikanBinding
    private lateinit var lokerList: MutableList<LokerPendidikan>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerPendidikanBinding.inflate(inflater, container, false)
        binding.icBack.setOnClickListener{
            val tambahLoker = LowionganPekerjaanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container, tambahLoker)
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
                        val adapter = LokerPendidikanAdapter(
                            requireActivity(),
                            R.layout.item_loker_pendidikan,
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