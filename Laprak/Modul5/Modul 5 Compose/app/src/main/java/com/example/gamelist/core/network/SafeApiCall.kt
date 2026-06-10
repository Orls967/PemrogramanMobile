package com.example.gamelist.core.network

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        ApiResult.Error(e, e.message ?: "HTTP Exception occurred (${e.code()})")
    } catch (e: IOException) {
        ApiResult.Error(e, e.message ?: "No internet connection or timeout")
    } catch (e: Exception) {
        ApiResult.Error(e, e.message ?: "An unexpected error occurred")
    }
}