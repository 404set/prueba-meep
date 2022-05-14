package com.alvikapps.prueba_meep.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvikapps.prueba_meep.model.Location
import com.alvikapps.prueba_meep.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    val myResponse: MutableLiveData<Location> = MutableLiveData()

    fun getPost(){
        viewModelScope.launch {
            val response: Location = repository.getLocations()
            myResponse.value = response
        }
    }
}