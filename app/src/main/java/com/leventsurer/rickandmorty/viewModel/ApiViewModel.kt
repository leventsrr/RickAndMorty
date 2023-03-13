package com.leventsurer.rickandmorty.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.rickandmorty.data.model.LocationModel
import com.leventsurer.rickandmorty.data.model.Resource
import com.leventsurer.rickandmorty.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ApiViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) :ViewModel(){


    private val _location  : MutableLiveData<Resource<LocationModel>?> = MutableLiveData()
    val locations : LiveData<Resource<LocationModel>?> get() = _location

     suspend fun getLocations() = viewModelScope.launch {
        _location.value = Resource.Loading
        val result = apiRepository.getLocations().body()
        _location.value = result
    }



}