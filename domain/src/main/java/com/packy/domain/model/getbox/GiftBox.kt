package com.packy.domain.model.getbox

import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GiftBox(
    @SerialName("box") val box: Box,
    @SerialName("envelope") val envelope: Envelope,
    @SerialName("gift") val gift: Gift?,
    @SerialName("letterContent") val letterContent: String,
    @SerialName("name") val name: String,
    @SerialName("photos") val photos: List<Photo>,
    @SerialName("receiverName") val receiverName: String,
    @SerialName("senderName") val senderName: String,
    @SerialName("stickers") val stickers: List<Sticker>,
    @SerialName("youtubeUrl") val youtubeUrl: String
)

fun GiftBox.toUrlEncoding(): GiftBox {
    val urlEncodingBox = this.box.toUrlEncoding()
    val urlEncodingEnvelope = this.envelope.toUrlEncoding()
    val urlEncodingGift = this.gift?.toUrlEncoding()
    val urlEncodingPhotos = this.photos.map { it.toUrlEncoding() }
    val urlEncodingStickers = this.stickers.map { it.toUrlEncoding() }
    val urlEncodingYoutubeUrl = this.youtubeUrl.toEncoding()

    return GiftBox(
        box = urlEncodingBox,
        envelope = urlEncodingEnvelope,
        gift = urlEncodingGift,
        letterContent = this.letterContent.toEncoding(),
        name = this.name.toEncoding(),
        photos = urlEncodingPhotos,
        receiverName = this.receiverName.toEncoding(),
        senderName = this.senderName.toEncoding(),
        stickers = urlEncodingStickers,
        youtubeUrl = urlEncodingYoutubeUrl
    )
}