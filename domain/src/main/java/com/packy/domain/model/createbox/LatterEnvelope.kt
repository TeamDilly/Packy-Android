package com.packy.domain.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterEnvelope(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
    @SerialName("borderColorCode") val borderColorCode: String
)