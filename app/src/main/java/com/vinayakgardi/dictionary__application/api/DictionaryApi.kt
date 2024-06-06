package com.vinayakgardi.dictionary__application.api

import com.vinayakgardi.dictionary__application.model.WordDataItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("en/{word}")
    suspend fun getMeaning(@Path("word") word : String) : Response<List<WordDataItem>>
}