package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.model.AssignmentResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("mobileapps/android_assignment.json")
    suspend fun fetchCustomUI(): Response<AssignmentResponse>
}