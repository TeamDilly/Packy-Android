package com.packy.domain.model.getbox

import com.packy.lib.ext.toDecoding
import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Box(
    @SerialName("id") val id: Long,
    @SerialName("boxNormal") val boxNormal: String,
    @SerialName("boxTop") val boxTop: String,
    @SerialName("boxLottie") val boxLottie: String
) {
    fun toUrlEncoding(): Box = Box(
        id = id,
        boxNormal = boxNormal.toEncoding(),
        boxTop = boxTop.toEncoding(),
        boxLottie = boxLottie.toEncoding()
    )

    fun toUrlDecoding(): Box = Box(
        id = id,
        boxTop = boxTop.toDecoding(),
        boxNormal = boxNormal.toDecoding(),
        boxLottie = boxLottie.toDecoding()
    )
}