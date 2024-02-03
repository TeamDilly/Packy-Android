package com.packy.domain.model.getbox

import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Box(
    @SerialName("boxBottom") val boxBottom: String,
    @SerialName("boxFull") val boxFull: String,
    @SerialName("boxPart") val boxPart: String
) {
    fun toUrlEncoding(): Box = Box(
        boxBottom = boxBottom.toEncoding(),
        boxFull = boxFull.toEncoding(),
        boxPart = boxPart.toEncoding()
    )

}