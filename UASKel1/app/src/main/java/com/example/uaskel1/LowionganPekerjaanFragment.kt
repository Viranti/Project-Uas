package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLowionganPekerjaanBinding


class LowionganPekerjaanFragment : Fragment() {
    lateinit var binding: FragmentLowionganPekerjaanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLowionganPekerjaanBinding.inflate(inflater, container, false)
        binding.card1.setOnClickListener{
            val lokerTeknologi = LokerTeknologiFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container, lokerTeknologi)
            transaction.commit()
        }
        return binding.root

    }
}