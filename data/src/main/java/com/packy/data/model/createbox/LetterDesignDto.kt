package com.packy.data.model.createbox

import com.packy.domain.model.createbox.LetterDesign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterDesignDto(
    @SerialName("borderColorCode") val borderColorCode: String,
    @SerialName("opacity") val opacity: Int,
){
    fun toEntity(): LetterDesign = LetterDesign(
        borderColorCode = borderColorCode,
        opacity = opacity
    )
}

