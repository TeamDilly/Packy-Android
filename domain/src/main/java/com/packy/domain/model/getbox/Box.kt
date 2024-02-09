package com.packy.domain.model.getbox

import com.packy.lib.ext.toDecoding
import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Box(
    @SerialName("boxNormal") val boxNormal: String,
    @SerialName("boxTop") val boxTop: String,
) {
    fun toUrlEncoding(): Box = Box(
        boxNormal = boxNormal.toEncoding(),
        boxTop = boxTop.toEncoding(),
    )

    fun toUrlDecoding(): Box = Box(
        boxTop = boxTop.toDecoding(),
        boxNormal = boxNormal.toDecoding(),
    )
}