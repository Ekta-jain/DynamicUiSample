package com.e4ekta.dynamicuisample.src.viewmodel

import androidx.lifecycle.*
import com.e4ekta.network_module.model.AssignmentResponse
import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _res = MutableLiveData<AssignmentResponse>()

    val res : LiveData<AssignmentResponse>
        get() = _res

    init {
        fetchCustomUI()
    }
    fun fetchCustomUI() = liveData<Resource<AssignmentResponse>>{
            var customUi =   mainRepository.fetchCustomUI()
            emit(customUi)

    }

}