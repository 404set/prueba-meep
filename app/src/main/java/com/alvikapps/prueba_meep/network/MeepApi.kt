package com.alvikapps.prueba_meep.network

import com.alvikapps.prueba_meep.model.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeepApi {

    @GET("resources")
    suspend fun getResources(
        @Query("lowerLeftLatLon") lowerLeftLatLon: String,
        @Query("upperRightLatLon") upperRightLatLon: String
    ): Response<List<Resource>>

}