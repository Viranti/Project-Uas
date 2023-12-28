package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentInfomasiTerkiniAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InfomasiTerkiniAdminFragment : Fragment() {
    lateinit var binding: FragmentInfomasiTerkiniAdminBinding
    private lateinit var informasiList: MutableList<InformasiTerkini>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfomasiTerkiniAdminBinding.inflate(inflater, container, false)
        binding.btnTambahinformasiterkini.setOnClickListener{
            val tambahInformasi = TambahInformasiTerkiniAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin, tambahInformasi)
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("informasi")
        informasiList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        informasiList.clear()
                        for (a in snapshot.children) {
                            val informasi = a.getValue(InformasiTerkini::class.java)
                            informasi?.let {
                                informasiList.add(it)
                            }
                        }
                        val adapter = InformasiTerkiniAdapterAdmin(
                            requireActivity(),
                            R.layout.item_informasi_admin,
                            informasiList
                        )
                        binding.lvOuput.adapter = adapter
                        println("Output: " + informasiList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}