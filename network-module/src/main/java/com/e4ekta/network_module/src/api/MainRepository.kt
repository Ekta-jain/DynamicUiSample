package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.src.model.BaseDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
): BaseDataSource(){

    suspend fun fetchCustomUI()  = getResult { apiService.fetchCustomUI() }
}