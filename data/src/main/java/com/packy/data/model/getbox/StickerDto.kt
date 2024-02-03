package com.packy.data.model.getbox

import com.packy.domain.model.getbox.Sticker
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StickerDto(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("location") val location: Int
) {
    fun toEntity(): Sticker = Sticker(
        imgUrl = imgUrl,
        location = location
    )
}

fun List<StickerDto>.toEntity(): List<Sticker> = map { it.toEntity() }