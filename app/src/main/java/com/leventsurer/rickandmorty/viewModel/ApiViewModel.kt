package com.leventsurer.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.rickandmorty.data.model.LocationByIdModel
import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.MultipleCharacterModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) :ViewModel(){


    private val _locations  : MutableLiveData<Resource<LocationsModel>?> = MutableLiveData()
    val locations : LiveData<Resource<LocationsModel>?> get() = _locations

    private val _location  : MutableLiveData<Resource<LocationByIdModel>?> = MutableLiveData()
    val location : LiveData<Resource<LocationByIdModel>?> get() = _location

    private val _characters  : MutableLiveData<Resource<ArrayList<MultipleCharacterModel>>?> = MutableLiveData()
    val characters : LiveData<Resource<ArrayList<MultipleCharacterModel>>?> get() = _characters


     suspend fun getLocations() = viewModelScope.launch {
        _locations.value = Resource.Loading
        val result = apiRepository.getLocations()
        _locations.value = result
    }

    suspend fun getALocationById(locationId:Int) = viewModelScope.launch {
        _location.value = Resource.Loading
        val result = apiRepository.getALocationById(locationId)
        _location.value = result
    }

    suspend fun getMultipleCharacterById(characterIds:ArrayList<Int>) = viewModelScope.launch {
        _characters.value = Resource.Loading
        val result = apiRepository.getMultipleCharacterById(characterIds)
        _characters.value = result
    }



}