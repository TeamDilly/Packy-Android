package com.packy.lib.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: String? = null) {
    class Success<T>(data: T, message: String, code: String) :
        Resource<T>(data = data, message = message, code = code)

    class ApiError<T>(data: T?, message: String, code: String) :
        Resource<T>(data = data, message = message, code = code)

    class NetworkError<T>(val throwable: Throwable) : Resource<T>()

    class NullResult<T> : Resource<T>()

    class Loading<T>() : Resource<T>()
}