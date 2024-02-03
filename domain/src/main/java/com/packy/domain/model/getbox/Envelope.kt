package com.packy.data.model.getbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Envelope(
   @SerialName("borderColorCode") val borderColorCode: String,
   @SerialName("imgUrl") val imgUrl: String
)