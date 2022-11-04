package com.e4ekta.dynamicuisample.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.e4ekta.network_module.src.api.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    init {
        fetchCustomUI()
    }

    fun fetchCustomUI() = liveData {
        val customUi = mainRepository.fetchCustomUI()
        emit(customUi)
    }

}