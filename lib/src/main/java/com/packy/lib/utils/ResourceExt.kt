package com.packy.lib.utils

fun <T, R> Resource<T>.map(
    mapper: (T?) -> R
): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(mapper(data), message ?: "", code ?: "")
    is Resource.ApiError -> Resource.ApiError(mapper(data), message ?: "", code ?: "")
    is Resource.NetworkError -> Resource.NetworkError(throwable)
    is Resource.NullResult -> Resource.NullResult()
    is Resource.Loading -> Resource.Loading()
}