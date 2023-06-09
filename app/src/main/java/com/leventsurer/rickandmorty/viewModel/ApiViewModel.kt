package com.leventsurer.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.rickandmorty.data.model.LocationByIdModel
import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.CharacterDetailModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) :ViewModel(){


    private val _locations  : MutableLiveData<Resource<LocationsModel>?> = MutableLiveData(null)
    val locations : LiveData<Resource<LocationsModel>?> get() = _locations

    private val _location  : MutableLiveData<Resource<LocationByIdModel>?> = MutableLiveData(null)
    val location : LiveData<Resource<LocationByIdModel>?> get() = _location

    private val _characters  : MutableLiveData<Resource<ArrayList<CharacterDetailModel>>?> = MutableLiveData(null)
    val characters : LiveData<Resource<ArrayList<CharacterDetailModel>>?> get() = _characters


     suspend fun getLocations(pageNumber:Int) = viewModelScope.launch {
        _locations.value = Resource.Loading
        val result = apiRepository.getLocations(pageNumber)
         Log.e("control","ViewModel result $result")
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
        Log.e("control","result:$result")
        _characters.value = result
    }



}