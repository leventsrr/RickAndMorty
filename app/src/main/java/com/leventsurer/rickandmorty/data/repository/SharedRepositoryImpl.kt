package com.leventsurer.rickandmorty.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedRepository {
    override fun writeIsLoginInfo(key: String, value: Boolean) {
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key,value)
        editor.apply()
    }

    override fun readIsLoginInfo(key: String): Boolean {
        return sharedPreferences.getBoolean(key,false)
    }
}