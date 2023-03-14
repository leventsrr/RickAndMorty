package com.leventsurer.rickandmorty.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.rickandmorty.data.model.LocationsModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) :ViewModel(){


    private val _location  : MutableLiveData<Resource<LocationsModel>?> = MutableLiveData()
    val locations : LiveData<Resource<LocationsModel>?> get() = _location

     suspend fun getLocations() = viewModelScope.launch {
        _location.value = Resource.Loading
        val result = apiRepository.getLocations()
         Log.e("kontrol",result.toString())
        _location.value = result
    }



}