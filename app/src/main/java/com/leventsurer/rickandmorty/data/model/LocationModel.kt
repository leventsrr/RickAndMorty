package com.leventsurer.rickandmorty.data.model

import com.google.gson.annotations.SerializedName


data class LocationModel (

    @SerializedName("info"    ) var info    : Info?              = Info(),
    @SerializedName("results" ) var results : ArrayList<Results> = arrayListOf()

)
