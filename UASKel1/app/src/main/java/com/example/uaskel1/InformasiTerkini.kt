package com.example.uaskel1

data class InformasiTerkini(
    val id: String, // id dari loker
    val judul: String, // judul loker
    val detail: String, // detail loker
    val tanggal: String // tanggal loker
){
    constructor():this("","","","")
}
