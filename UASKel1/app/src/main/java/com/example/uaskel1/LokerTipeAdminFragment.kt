package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerTipeAdminBinding


class LokerTipeAdminFragment : Fragment() {
    lateinit var binding: FragmentLokerTipeAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerTipeAdminBinding.inflate(layoutInflater)
        binding.card1.setOnClickListener{
            val lowonganSpesialis = LokerSpesialisAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lowonganSpesialis)
            transaction.commit()
        }
        binding.card2.setOnClickListener{
            val lowonganSpesialis = LokerSpesialisAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lowonganSpesialis)
            transaction.commit()
        }
        binding.card3.setOnClickListener{
            val lowonganSpesialis = LokerSpesialisAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lowonganSpesialis)
            transaction.commit()
        }
        binding.icBack.setOnClickListener{
            val beranda = BerandaAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,beranda)
            transaction.commit()
        }
        return binding.root
    }
}