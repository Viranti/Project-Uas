package com.example.uaskel1

data class Lukis (
    val id: String,
    val judul: String,
    val detail: String,
    val tanggal: String
){
    constructor():this("","","","")
}