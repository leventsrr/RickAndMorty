package com.leventsurer.rickandmorty.data.repository

import android.util.Log
import com.leventsurer.rickandmorty.data.model.LocationByIdModel
import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.service.RickAndMortyService
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api : RickAndMortyService
) {

    suspend fun getLocations(pageNumber:Int) :Resource<LocationsModel> {
        val response : Response<LocationsModel> = api.getLocations(pageNumber)
        return if(response.isSuccessful){
            Log.e("kontrol","ApiRepository response ${response.body()}")
            Resource.Success(response.body()!!)
        }else{
            Resource.Failure(HttpException(response))
        }
    }

    suspend fun getALocationById(locationId:Int):Resource<LocationByIdModel>{
        val response : Response<LocationByIdModel> = api.getALocationById(locationId)
        return if(response.isSuccessful){
            Resource.Success(response.body()!!)
        }else{
            Resource.Failure(HttpException(response))
        }
    }

    suspend fun getMultipleCharacterById(characterIds:ArrayList<Int>) :Resource<ArrayList<CharacterDetailModel>>{
        val response : Response<ArrayList<CharacterDetailModel>> = api.getMultipleCharacter(characterIds)
        return if(response.isSuccessful){
            Resource.Success(response.body()!!)
        }else{
            Resource.Failure(HttpException(response))
        }
    }


}