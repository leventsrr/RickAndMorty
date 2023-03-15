package com.leventsurer.rickandmorty.service

import com.leventsurer.rickandmorty.data.model.LocationByIdModel
import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.MultipleCharacterModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("location")
    suspend fun getLocations() : Response<LocationsModel>

    @GET("location/{locationId}")
    suspend fun getALocationById(@Path("locationId") locationId:Int) : Response<LocationByIdModel>

    @GET("character/{characterIds}")
    suspend fun getMultipleCharacter(@Path("characterIds") characterIds: ArrayList<Int>) :Response<ArrayList<MultipleCharacterModel>>

}