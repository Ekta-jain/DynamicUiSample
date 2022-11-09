package com.e4ekta.network_module.src.model

import com.e4ekta.network_module.src.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            //Resource.loading(null,"true")
            val response = call()
            if (response.isSuccessful) {
              //  Resource.loading(null, "true")
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            if (response.errorBody() != null) {
                return error("Received failure response !!")
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Throwable) {
            return when (e) {
                is IOException -> {
                    error("Check Your Internet Connection..")
                }
                is HttpException -> {
                    error("Unexpected response")
                }
                else -> {
                    error("Something Went Wrong")
                }
            }
        } catch (e: Exception) {

            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }

}