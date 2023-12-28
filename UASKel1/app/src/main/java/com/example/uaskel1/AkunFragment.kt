package com.example.uaskel1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.example.uaskel1.databinding.FragmentAkunBinding


class AkunFragment : Fragment() {
    lateinit var binding: FragmentAkunBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firebaseAuth = FirebaseAuth.getInstance();
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun logout(){
        firebaseAuth.signOut()
    }
}