package com.alvikapps.prueba_meep.api

import com.alvikapps.prueba_meep.utils.Contants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: SimpleApi by lazy{
        retrofit.create(SimpleApi::class.java)
    }
}