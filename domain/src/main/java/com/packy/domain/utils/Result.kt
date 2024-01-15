package com.packy.domain.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null, val code: String? = null) {
    class Success<T>(data: T, message: String, code: String) :
        Resource<T>(data = data, message = message, code = code)

    class Error<T>(data: T?, message: String, code: String) :
        Resource<T>(data = data, message = message, code = code)

    class UnKnowError<T>(message: String?) : Resource<T>(message = message)

    class Loading<T>() : Resource<T>()
}