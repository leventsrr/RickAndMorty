package com.leventsurer.rickandmorty.data.model

data class Result(
    var isSelected:Boolean = false,
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)