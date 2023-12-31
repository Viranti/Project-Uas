package com.example.uaskel1

data class User(
    val id: String,
    val email: String,
    val nama: String,
    val password: String,
    val role: String
){
    constructor():this("","","","","")
}
