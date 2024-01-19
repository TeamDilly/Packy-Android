package com.packy.lib.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Resource<T>(
    @SerialName("data") val data: T? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("code") val code: String? = null) {
    class Success<T>(data: T, message: String, code: String) :
        Resource<T>(data = data, message = message, code = code)

    class ApiError<T>(data: T?, message: String, code: String) :
        Resource<T>(data = data, message = message, code = code)

    class NetworkError<T>(val throwable: Throwable) : Resource<T>()

    class NullResult<T> : Resource<T>()

    class Loading<T>() : Resource<T>()
}