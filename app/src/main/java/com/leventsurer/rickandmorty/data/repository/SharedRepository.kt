package com.leventsurer.rickandmorty.data.repository

interface SharedRepository {

    fun writeIsLoginInfo(key:String,value:Boolean)
    fun readIsLoginInfo(key: String):Boolean?
}