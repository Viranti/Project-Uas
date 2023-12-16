package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentBerandaBinding
class BerandaFragment : Fragment() {
    private lateinit var binding: FragmentBerandaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaBinding.inflate(layoutInflater)
        binding.card2.setOnClickListener{
            val pelatihan = PelatihanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,pelatihan)
            transaction.commit()
        }
        binding.card3.setOnClickListener{
            val pengajuan = PengajuanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,pengajuan)
            transaction.commit()
        }
        binding.card5.setOnClickListener{
            val informasi = InformasiTerkiniFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,informasi)
            transaction.commit()
        }
        binding.card4.setOnClickListener{
            val lowongan = LokerTipeFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,lowongan)
            transaction.commit()
        }
        return binding.root
    }
}