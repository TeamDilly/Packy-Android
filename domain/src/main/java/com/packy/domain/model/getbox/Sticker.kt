package com.packy.domain.model.getbox

import com.packy.lib.ext.toDecoding
import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("location") val location: Int
) {
    fun toUrlEncoding(): Sticker = Sticker(
        imgUrl = imgUrl.toEncoding(),
        location = location
    )

    fun toUrlDecoding():Sticker = Sticker(
        imgUrl = imgUrl.toDecoding(),
        location = location
    )
}