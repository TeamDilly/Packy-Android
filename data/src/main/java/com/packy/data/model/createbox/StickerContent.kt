package com.packy.data.model.createbox

import com.packy.domain.model.createbox.Sticker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StickerContent(
    @SerialName("id") val id: Int,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("sequence") val sequence: Int
)

fun StickerContent.toEntity() = Sticker(
    id = this.id,
    imgUrl = this.imgUrl,
)
