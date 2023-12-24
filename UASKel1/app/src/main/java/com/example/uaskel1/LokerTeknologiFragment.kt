package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerTeknologiBinding

class LokerTeknologiFragment : Fragment() {
    lateinit var binding: FragmentLokerTeknologiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLokerTeknologiBinding.inflate(layoutInflater)
        binding.btnTambahloker.setOnClickListener{
            val tambahLoker = TambahLokerTeknologiFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,tambahLoker)
            transaction.commit()
        }
        return binding.root
    }
}