package com.packy.domain.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterDesign(
    @SerialName("borderColorCode") val borderColorCode: String,
    @SerialName("opacity") val opacity: Int,
)