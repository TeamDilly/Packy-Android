package com.packy.data.model.getbox

import com.packy.domain.model.getbox.Gift
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiftDto(
    @SerialName("type") val type: String,
    @SerialName("url") val url: String
) {
    fun toEntity(): Gift = Gift(
        type = type,
        url = url
    )
}