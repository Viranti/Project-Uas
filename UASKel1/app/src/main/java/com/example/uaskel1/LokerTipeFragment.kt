package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentLokerTipeBinding


class LokerTipeFragment : Fragment() {
    lateinit var binding: FragmentLokerTipeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLokerTipeBinding.inflate(layoutInflater)
        binding.card3.setOnClickListener{
            val lowonganSpesialis = LowionganPekerjaanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,lowonganSpesialis)
            transaction.commit()
        }
        return binding.root
    }
}