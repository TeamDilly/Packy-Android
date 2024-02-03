package com.packy.data.model.getbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("description") val description: String,
    @SerialName("photoUrl") val photoUrl: String,
    @SerialName("sequence") val sequence: Int
)