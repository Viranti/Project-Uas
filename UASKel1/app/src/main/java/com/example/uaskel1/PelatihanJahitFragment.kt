package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uaskel1.databinding.FragmentPelatihanJahitBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PelatihanJahitFragment : Fragment() {
    lateinit var binding: FragmentPelatihanJahitBinding
    private lateinit var jahitList: MutableList<Jahit>
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPelatihanJahitBinding.inflate(inflater, container, false)
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
                        val adapter = JahitAdapter(
                            requireActivity(),
                            R.layout.item_jahit,
                            jahitList
                        )
                        binding.lvPelatihanjahit.adapter = adapter
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