package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.model.AssignmentResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun fetchCustomUI(): Response<AssignmentResponse>  = apiService.fetchCustomUI()

}