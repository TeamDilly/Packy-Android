package com.packy.domain.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StickerList(
    @SerialName("stickers") val stickers: List<Sticker>,
    @SerialName("last") val last: Boolean
)
