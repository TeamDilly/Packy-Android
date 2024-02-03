package com.packy.domain.model.getbox

import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gift(
    @SerialName("type") val type: String,
    @SerialName("url") val url: String
) {
    fun toUrlEncoding(): Gift = Gift(
        type = type,
        url = url.toEncoding()
    )
}