package com.packy.domain.model.createbox.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stickers(
    @SerialName("id") val id: Int?,
    @SerialName("location") val location: Int?
)