package com.leventsurer.rickandmorty.service

import com.leventsurer.rickandmorty.data.model.LocationByIdModel
import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("location")
    suspend fun getLocations(@Query("page") pageNumber:Int) : Response<LocationsModel>

    @GET("location/{locationId}")
    suspend fun getALocationById(@Path("locationId") locationId:Int) : Response<LocationByIdModel>

    @GET("character/{characterIds}")
    suspend fun getMultipleCharacter(@Path("characterIds") characterIds: ArrayList<Int>) :Response<ArrayList<CharacterDetailModel>>

}