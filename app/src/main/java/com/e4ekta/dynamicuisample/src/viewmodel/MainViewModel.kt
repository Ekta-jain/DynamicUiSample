package com.e4ekta.dynamicuisample.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.e4ekta.network_module.model.AssignmentResponse
import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _loadingStateLiveData = MutableLiveData<Boolean>()
    val loadingStateLiveData: LiveData<Boolean> = _loadingStateLiveData

    init {
        fetchCustomUI()
    }

    fun fetchCustomUI() = liveData {
        _loadingStateLiveData.postValue(true)
        val customUi = mainRepository.fetchCustomUI()
        emit(customUi)
        _loadingStateLiveData.postValue(false)
    }

}