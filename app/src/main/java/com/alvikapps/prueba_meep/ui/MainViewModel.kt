package com.alvikapps.prueba_meep.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvikapps.prueba_meep.model.Resource
import com.alvikapps.prueba_meep.repository.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponse: MutableLiveData<Response<List<Resource>>> = MutableLiveData()

    fun getResources(lowerLeftLatLon: String, upperRightLatLon: String){
        viewModelScope.launch {
            val response: Response<List<Resource>> = repository.getResources(lowerLeftLatLon, upperRightLatLon)
            myResponse.value = response
        }
    }
}