package com.leventsurer.rickandmorty.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leventsurer.rickandmorty.data.repository.SharedRepositoryImpl
import com.leventsurer.rickandmorty.tools.constant.SharedPrefConstants.IS_LOGIN_INFO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedPreferencesViewModel @Inject constructor(
    private val sharedPrefRepository:SharedRepositoryImpl
):ViewModel() {

    fun writeIsLoginInfo(value:Boolean) = viewModelScope.launch {
        sharedPrefRepository.writeIsLoginInfo(IS_LOGIN_INFO,value)
    }

    fun readIsLoginInfo(): Boolean {
        return sharedPrefRepository.readIsLoginInfo(IS_LOGIN_INFO)
    }

}