package com.packy.domain.model.getbox

import com.packy.lib.ext.toDecoding
import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Envelope(
    @SerialName("borderColorCode") val borderColorCode: String,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("opacity") val opacity: Int,
) {
    fun toUrlEncoding(): Envelope = Envelope(
        borderColorCode = borderColorCode,
        imgUrl = imgUrl.toEncoding(),
        opacity = opacity
    )

    fun toUrlDecoding(): Envelope = Envelope(
        borderColorCode = borderColorCode,
        imgUrl = imgUrl.toDecoding(),
        opacity = opacity
    )
}