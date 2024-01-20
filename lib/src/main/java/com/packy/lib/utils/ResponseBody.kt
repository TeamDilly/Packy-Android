package com.packy.lib.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody<T>(
    @SerialName("code") val code: String,
    @SerialName("message") val message: String,
    @SerialName("data") val data: T? = null
)
