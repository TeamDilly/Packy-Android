package com.packy.data.model.getbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDto(
    @SerialName("boxBottom") val boxBottom: String,
    @SerialName("boxFull") val boxFull: String,
    @SerialName("boxPart") val boxPart: String
)