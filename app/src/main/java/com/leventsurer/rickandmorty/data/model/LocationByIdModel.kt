package com.leventsurer.rickandmorty.data.model

data class LocationByIdModel(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)