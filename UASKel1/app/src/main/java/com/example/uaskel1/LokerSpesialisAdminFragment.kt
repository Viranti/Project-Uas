package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerSpesialisAdminBinding

class LokerSpesialisAdminFragment : Fragment() {
    lateinit var binding: FragmentLokerSpesialisAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerSpesialisAdminBinding.inflate(inflater, container, false)
        binding.card1.setOnClickListener{
            val lokerTeknologiFragment = LokerTeknologiAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lokerTeknologiFragment)
            transaction.commit()
        }
        return binding.root
    }

}