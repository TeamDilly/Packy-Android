package com.packy.data.model.getbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("location") val location: Int
)