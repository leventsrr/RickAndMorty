package com.leventsurer.rickandmorty.data.repository

import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.service.RickAndMortyService
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api : RickAndMortyService
) {

    suspend fun getLocations() :Resource<LocationsModel> {
        val response : Response<LocationsModel> = api.getLocations()
        return if(response.isSuccessful){
            Resource.Success(response.body()!!)
        }else{
            Resource.Failure(HttpException(response))
        }
    }


}