package com.packy.data.model.createbox.box

import com.packy.domain.model.createbox.box.CreateBox
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBoxRequest(
    @SerialName("boxId") val boxId: Long,
    @SerialName("envelopeId") val envelopeId: Int,
    @SerialName("gift") val gift: Gift?,
    @SerialName("letterContent") val letterContent: String,
    @SerialName("name") val name: String,
    @SerialName("photos") val photos: List<Photo>,
    @SerialName("receiverName") val receiverName: String,
    @SerialName("senderName") val senderName: String,
    @SerialName("stickers") val stickers: List<Sticker>,
    @SerialName("youtubeUrl") val youtubeUrl: String
) {
    companion object {
        fun formEntity(
            createBox: CreateBox,
            photoUrl: String,
            giftUrl: String?
        ): CreateBoxRequest? {
            if (!createBox.boxAllReady()) return null
            val gift = createBox.gift
            val photos = listOf(
                Photo(
                    description = createBox.photo!!.description,
                    photoUrl = photoUrl,
                    sequence = createBox.photo!!.sequence
                )
            )
            val stickers = listOf(
                Sticker(
                    id = createBox.stickers[0].id!!,
                    location = createBox.stickers[0].location!!
                ),
                Sticker(
                    id = createBox.stickers[1].id!!,
                    location = createBox.stickers[1].location!!
                )
            )
            return CreateBoxRequest(
                boxId = createBox.boxId!!,
                envelopeId = createBox.envelopeId!!,
                gift = if (gift != null && giftUrl != null) Gift(
                    type = gift.type,
                    url = giftUrl
                ) else null,
                letterContent = createBox.letterContent!!,
                name = createBox.name!!,
                photos = photos,
                receiverName = createBox.receiverName!!,
                senderName = createBox.senderName!!,
                stickers = stickers,
                youtubeUrl = createBox.youtubeUrl!!
            )
        }
    }
}