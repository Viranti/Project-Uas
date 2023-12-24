package com.example.uaskel1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.uaskel1.databinding.ActivityMainAdminBinding

class MainAdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(BerandaAdminFragment())

        binding.navbarAdmin.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_home_admin -> changeFragment(BerandaAdminFragment())
                R.id.tab_akun_admin -> changeFragment(AkunFragment())
                else -> {}
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_admin, fragment)
        fragmentTransaction.commit()
    }
}