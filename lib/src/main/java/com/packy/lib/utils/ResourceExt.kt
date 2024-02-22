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

fun <T> Flow<Resource<T>>.filterLoading(bloc: suspend () -> Unit = {}) =
    map {
        if (it is Resource.Loading) {
            bloc()
        }
        it
    }
        .filter {
            it !is Resource.Loading
        }

fun <T> Flow<Resource<T>>.loadingHandler(
    loadingHandler: suspend (Boolean) -> Unit
): Flow<Resource<T>> =
    map {
        if (it is Resource.Loading) {
            loadingHandler(true)
        } else {
            loadingHandler(false)
        }
        it
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

inline operator fun <reified T, reified R> Resource<T>.plus(resource: Resource<R>): Resource<Pair<T, R>> =
    when {
        this is Resource.Loading || resource is Resource.Loading -> Resource.Loading()
        this is Resource.ApiError -> Resource.ApiError(
            null,
            message,
            code
        )

        resource is Resource.ApiError -> Resource.ApiError(
            null,
            resource.message,
            resource.code
        )

        this is Resource.NetworkError -> Resource.NetworkError(throwable)
        resource is Resource.NetworkError -> Resource.NetworkError(resource.throwable)

        this is Resource.NullResult -> Resource.NullResult(
            message,
            code
        )

        resource is Resource.NullResult -> Resource.NullResult(
            resource.message,
            resource.code
        )

        this is Resource.Success && resource is Resource.Success -> Resource.Success(
            Pair(
                this.data,
                resource.data
            ),
            message,
            code
        )

        else -> {
            Resource.NullResult(
                "",
                "Resource failed to combine"
            )
        }
    }
