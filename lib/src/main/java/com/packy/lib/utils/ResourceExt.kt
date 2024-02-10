package com.packy.lib.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map

fun <T, R> Resource<T>.map(
    mapper: (T) -> R
): Resource<R> = when (this) {
    is Resource.Success -> Resource.Success(
        mapper(data),
        message,
        code
    )

    is Resource.ApiError -> Resource.ApiError(
        null,
        message,
        code
    )

    is Resource.NetworkError -> Resource.NetworkError(throwable)
    is Resource.NullResult -> Resource.NullResult(
        message,
        code
    )

    is Resource.Loading -> Resource.Loading()
}

fun <T> Flow<Resource<T>>.filterSuccess(): Flow<Resource.Success<T>> =
    filterIsInstance<Resource.Success<T>>()

fun <T> Flow<Resource<T>>.filterLoading(bloc: () -> Unit = {}) =
    map {
        if (it is Resource.Loading) {
            bloc()
        }
        it
    }
        .filter {
            it !is Resource.Loading
        }

fun <T> Flow<Resource<T>>.filterError(): Flow<Resource<T>> =
    filter { resource ->
        resource !is Resource.Success<T> && resource !is Resource.Loading
    }

fun <T> Flow<Resource<T>>.errorMessageHandler(
    errorHandler: (String?) -> Unit
): Flow<Resource<T>> =
    map {
        if (it is Resource.ApiError || it is Resource.NetworkError || it is Resource.NullResult) {
            errorHandler(it.message)
        }
        it
    }

fun <T> Flow<Resource<T>>.catchError(
    catchErrorFunction: (Resource<T>) -> Unit
): Flow<Resource<T>> =
    filter { resource ->
        resource !is Resource.Success<T> && resource !is Resource.Loading
    }.map {
        catchErrorFunction(it)
        it
    }

fun <T> Flow<Resource.Success<T>>.unwrapResource(): Flow<T> = this.map { it.data }
