package com.packy.data.model.getbox

import com.packy.domain.model.getbox.Envelope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeDto(
    @SerialName("borderColorCode") val borderColorCode: String,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("opacity") val opacity: Int
)

fun EnvelopeDto.toEntity(): Envelope =
    Envelope(
        imgUrl = imgUrl,
        borderColorCode = borderColorCode,
        opacity = opacity
    )