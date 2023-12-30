package com.example.uaskel1

data class User(
    val id: String, // id dari loker
    val email: String, // judul loker
    val nama: String, // detail loker
    val password: String, // tanggal loker
    val role: String // tanggal loker
){
    constructor():this("","","","","")
}
