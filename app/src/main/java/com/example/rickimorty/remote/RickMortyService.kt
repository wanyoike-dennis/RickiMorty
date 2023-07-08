package com.example.rickimorty.remote

import com.example.rickimorty.utils.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface RickMortyService{
    // get character
    @GET("character")
    suspend fun getCharacter(@Query("page") page:Int):Response<ApiResponse>

}

object MortyApi{
    val retrofitService: RickMortyService by lazy {
        retrofit.create(RickMortyService::class.java)
    }
}

