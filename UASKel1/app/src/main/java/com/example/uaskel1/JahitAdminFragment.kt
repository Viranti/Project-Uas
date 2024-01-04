package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentJahitAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JahitAdminFragment : Fragment() {
    lateinit var binding: FragmentJahitAdminBinding
    private lateinit var jahitList: MutableList<Jahit>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJahitAdminBinding.inflate(inflater, container, false)
        binding.btnPindahtambahpelatihanjahit.setOnClickListener{
            val tambahPelatihan = TambahPelatihanJahitFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,tambahPelatihan)
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("jahit")
        jahitList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) {
                    if (snapshot.exists()) {
                        jahitList.clear()
                        for (a in snapshot.children) {
                            val jahit = a.getValue(Jahit::class.java)
                            jahit?.let {
                                jahitList.add(it)
                            }
                        }
                        val adapter = JahitAdapterAdmin(
                            requireActivity(),
                            R.layout.item_jahit_admin,
                            jahitList
                        )
                        binding.lvOutputpelatihan.adapter = adapter
                        println("Output: " + jahitList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}