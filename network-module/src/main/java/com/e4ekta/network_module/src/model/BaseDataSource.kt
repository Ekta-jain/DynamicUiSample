package com.e4ekta.network_module.src.model

import com.e4ekta.network_module.src.utils.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {

            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                response.code()
                if (body != null) return Resource.success(body)
            }
            if (response.errorBody() != null) {
                return error("Received Failure Response")
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Throwable) {
            when (e) {
                is IOException -> {
                    return error("Check Your Internet Connection!!")
                }
                is HttpException -> {
                    return error("Unexpected response")
                }
                else -> {
                    return error("something went wrong")
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