package com.example.uaskel1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.uaskel1.databinding.FragmentPelatihanBinding


class PelatihanFragment : Fragment() {
    lateinit var binding: FragmentPelatihanBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPelatihanBinding.inflate(layoutInflater)
        binding.list7.setOnClickListener{
            val pelatiahMerias = PelatihanMeriasFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,pelatiahMerias)
            transaction.commit()
        }
        binding.list1.setOnClickListener {
            val pelatihanJahit = PelatihanJahitFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container, pelatihanJahit)
            transaction.commit()
        }
        binding.list2.setOnClickListener{
            val pelatihanLukis = PelatihanLukisFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,pelatihanLukis)
            transaction.commit()
        }
        return binding.root
    }
}