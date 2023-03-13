package com.leventsurer.rickandmorty.data.repository

import com.leventsurer.rickandmorty.api.RickAndMortyApi
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api : RickAndMortyApi
) {

    suspend fun getLocations() = api.getLocations()


}