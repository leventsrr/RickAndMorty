package com.leventsurer.rickandmorty.di

import android.content.SharedPreferences
import com.leventsurer.rickandmorty.service.RickAndMortyService
import com.leventsurer.rickandmorty.data.repository.SharedPrefManager
import com.leventsurer.rickandmorty.tools.constant.RetrofitConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    //Shared Preferences
    @Provides
    @Singleton
    fun provideSharedPreferences(
        sharedPrefManager: SharedPrefManager
    ): SharedPreferences = sharedPrefManager.getSharedPref()

    //Retrofit
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }


    @Singleton
    @Provides
    fun  getRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    /*
    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit:Retrofit):  {
        return retrofit.create(ProductApi::class.java)
    }*/

    @Singleton
    @Provides
    fun getRetrofitServiceInstanceToCart(retrofit: Retrofit):RickAndMortyService{
        return retrofit.create(RickAndMortyService::class.java)
    }

}