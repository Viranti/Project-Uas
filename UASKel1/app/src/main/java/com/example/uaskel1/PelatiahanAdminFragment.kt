package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentPelatiahanAdminBinding

class PelatiahanAdminFragment : Fragment() {
    lateinit var binding: FragmentPelatiahanAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPelatiahanAdminBinding.inflate(layoutInflater)
        binding.admListPl7.setOnClickListener{
            val pelatihanMerias = MeriasAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,pelatihanMerias)
            transaction.commit()
        }
        binding.admListPl1.setOnClickListener {
            val pelatihanJahit = JahitAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,pelatihanJahit)
            transaction.commit()

        }
        binding.admListPl2.setOnClickListener {
            val pelatihanLukis = LukisAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_admin,pelatihanLukis)
            transaction.commit()

        }
        return binding.root
    }

}