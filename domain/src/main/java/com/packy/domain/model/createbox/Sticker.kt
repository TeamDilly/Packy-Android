package com.packy.domain.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
    @SerialName("id") val id: Int,
    @SerialName("imgUrl") val imgUrl: String,
)