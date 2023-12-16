package com.example.uaskel1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uaskel1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(BerandaFragment())

        binding.navbar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_home -> changeFragment(BerandaFragment())
                R.id.tab_faq -> changeFragment(FaqFragment())
                else -> {}
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }
}