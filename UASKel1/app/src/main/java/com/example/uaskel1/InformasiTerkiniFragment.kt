package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentInformasiTerkiniBinding

class InformasiTerkiniFragment : Fragment() {
    lateinit var binding: FragmentInformasiTerkiniBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformasiTerkiniBinding.inflate(layoutInflater)
        binding.informasi1.setOnClickListener{
            val detil = InformasiDetilFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,detil)
            transaction.commit()
        }
        return binding.root
    }

}