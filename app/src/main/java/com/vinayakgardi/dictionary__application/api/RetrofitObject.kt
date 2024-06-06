package com.vinayakgardi.dictionary__application.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/entries/"

    fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApi : DictionaryApi = getInstance().create(DictionaryApi::class.java)
}