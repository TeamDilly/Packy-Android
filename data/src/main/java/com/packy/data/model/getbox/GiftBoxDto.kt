package com.packy.data.model.getbox

import com.packy.domain.model.getbox.GiftBox
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiftBoxDto(
    @SerialName("box") val box: BoxDto,
    @SerialName("envelope") val envelope: EnvelopeDto,
    @SerialName("gift") val gift: GiftDto? = null,
    @SerialName("letterContent") val letterContent: String,
    @SerialName("name") val name: String,
    @SerialName("photos") val photos: List<PhotoDto>,
    @SerialName("receiverName") val receiverName: String,
    @SerialName("senderName") val senderName: String,
    @SerialName("stickers") val stickers: List<StickerDto>,
    @SerialName("youtubeUrl") val youtubeUrl: String
)

fun GiftBoxDto.toEntity(): GiftBox = GiftBox(
    box = box.toEntity(),
    envelope = envelope.toEntity(),
    gift = gift?.toEntity(),
    letterContent = letterContent,
    name = name,
    photos = photos.toEntity(),
    receiverName = receiverName,
    senderName = senderName,
    stickers = stickers.toEntity(),
    youtubeUrl = youtubeUrl,
)


