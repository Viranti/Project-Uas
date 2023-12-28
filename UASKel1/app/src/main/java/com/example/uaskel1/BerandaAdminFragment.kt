package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentBerandaAdminBinding


class BerandaAdminFragment : Fragment() {
    lateinit var binding: FragmentBerandaAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaAdminBinding.inflate(layoutInflater)
        binding.card2.setOnClickListener{
            val pelatihan = PelatiahanAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,pelatihan)
            transaction.commit()
        }
        binding.card3.setOnClickListener{
            val pengajuan = PengajuanAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,pengajuan)
            transaction.commit()
        }
        binding.card4.setOnClickListener{
            val lokerTipe = LokerTipeAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,lokerTipe)
            transaction.commit()
        }
        binding.card5.setOnClickListener {
            val informasiTerkiniAdm = InfomasiTerkiniAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin, informasiTerkiniAdm)
            transaction.commit()
        }
        return binding.root
    }
}