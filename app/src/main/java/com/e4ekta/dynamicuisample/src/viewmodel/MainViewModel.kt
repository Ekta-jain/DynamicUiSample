package com.e4ekta.dynamicuisample.src.viewmodel

import androidx.lifecycle.*
import com.e4ekta.network_module.model.AssignmentResponse
import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {


    private val fetchCustomUI = MutableLiveData<Resource<AssignmentResponse>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            fetchCustomUI.postValue(Resource.loading())
            try {
                val usersFromApi = mainRepository.fetchCustomUI()
                fetchCustomUI.postValue(usersFromApi)
            } catch (e: Exception) {
                fetchCustomUI.postValue(Resource.error(e.toString()))
            }
        }
    }

    fun getFetchUsers(): LiveData<Resource<AssignmentResponse>> = fetchCustomUI

}