package com.packy.lib.utils

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.KSerializer as KSerializer1

@Serializable
sealed class Resource<T>(
    @SerialName("data") open val data: T? = null,
    @SerialName("message") open val message: String? = null,
    @SerialName("code") open val code: String? = null
) {

    data class Success<T>(
        override val data: T,
        override val message: String,
        override val code: String
    ) :
        Resource<T>(data = data, message = message, code = code)

    data class ApiError<T>(
        override val data: T?,
        override val message: String,
        override val code: String
    ) :
        Resource<T>(data = data, message = message, code = code)

    class NetworkError<T>(val throwable: Throwable) : Resource<T>()

    data class NullResult<T>(override val message: String, override val code: String) :
        Resource<T>(message = message, code = code)

    class Loading<T>() : Resource<T>()
}