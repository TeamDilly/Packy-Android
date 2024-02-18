package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchiveGift
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveGiftDto(
    @SerialName("gift") val gift: Gift,
    @SerialName("giftBoxId") val giftBoxId: Int
) {
    @Serializable
    data class Gift(
        @SerialName("type") val type: String,
        @SerialName("url") val url: String
    )

    fun toEntity(): ArchiveGift = ArchiveGift(gift.url)
}