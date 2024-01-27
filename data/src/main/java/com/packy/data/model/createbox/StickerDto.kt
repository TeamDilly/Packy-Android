package com.packy.data.model.createbox

import com.packy.domain.model.createbox.StickerList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StickerDto(
    @SerialName("content") val content: List<StickerContent>,
    @SerialName("first") val first: Boolean,
    @SerialName("last") val last: Boolean
)

fun StickerDto.toEntity(): StickerList = StickerList(
    stickers = this.content.map { it.toEntity() },
    last = this.last
)