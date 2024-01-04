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
        binding.card2.setOnClickListener{
            val lokerAkutans = LokerAkuntansiAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lokerAkutans)
            transaction.commit()
        }
        binding.card4.setOnClickListener{
            val lokerPendidikan = LokerPendidikanAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lokerPendidikan)
            transaction.commit()
        }
        binding.icBack.setOnClickListener{
            val lokerTipe = LokerTipeAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lokerTipe)
            transaction.commit()
        }
        return binding.root
    }

}