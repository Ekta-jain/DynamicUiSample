package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.model.AssignmentResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun fetchCustomUI():Response<AssignmentResponse>
}