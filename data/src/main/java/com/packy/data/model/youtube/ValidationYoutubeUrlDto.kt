package com.packy.data.model.youtube

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValidationYoutubeUrlDto(
    @SerialName("status") val status: Boolean
)
