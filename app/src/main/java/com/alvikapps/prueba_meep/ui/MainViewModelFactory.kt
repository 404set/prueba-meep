package com.alvikapps.prueba_meep.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvikapps.prueba_meep.repository.Repository


class MainViewModelFactory(val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}