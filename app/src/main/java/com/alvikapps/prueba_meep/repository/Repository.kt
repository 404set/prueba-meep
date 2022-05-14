package com.alvikapps.prueba_meep.repository

import com.alvikapps.prueba_meep.api.RetrofitInstance
import com.alvikapps.prueba_meep.model.Post

class Repository {
    suspend fun getLocations(): Post {
        return RetrofitInstance.api.getLocations()
    }
}