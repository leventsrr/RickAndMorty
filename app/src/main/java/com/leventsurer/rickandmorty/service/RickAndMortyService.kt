package com.leventsurer.rickandmorty.service

import com.leventsurer.rickandmorty.data.model.LocationsModel
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {

    @GET("location")
    suspend fun getLocations() : Response<LocationsModel>
}