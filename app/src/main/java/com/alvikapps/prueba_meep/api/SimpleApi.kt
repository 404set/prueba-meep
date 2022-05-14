package com.alvikapps.prueba_meep.api

import com.alvikapps.prueba_meep.model.Location
import retrofit2.http.GET

interface SimpleApi {

    @GET("no")
    suspend fun getLocations(): Location

}
