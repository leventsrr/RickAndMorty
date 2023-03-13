package com.leventsurer.rickandmorty.api

import com.leventsurer.rickandmorty.data.model.LocationModel
import com.leventsurer.rickandmorty.data.model.Resource
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("location")
    suspend fun getLocations() : Response<Resource<LocationModel>>
}