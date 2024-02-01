package com.packy.data.model.createbox.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
   @SerialName("id") val id: Int,
   @SerialName("location") val location: Int
)