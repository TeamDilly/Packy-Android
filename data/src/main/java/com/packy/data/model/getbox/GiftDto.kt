package com.packy.data.model.getbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiftDto(
    @SerialName("type") val type: String,
    @SerialName("url") val url: String
)