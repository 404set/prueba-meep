package com.alvikapps.prueba_meep.repository

import com.alvikapps.prueba_meep.network.RetrofitInstance
import com.alvikapps.prueba_meep.model.Resource
import retrofit2.Response

class Repository {
    suspend fun getResources(lowerLeftLatLon: String, upperRightLatLon:String): Response<List<Resource>> {
        return RetrofitInstance.api.getResources(lowerLeftLatLon, upperRightLatLon)
    }
}