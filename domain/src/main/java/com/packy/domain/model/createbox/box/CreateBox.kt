package com.packy.domain.model.createbox.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBox(
    @SerialName("boxId") val boxId: Long?,
    @SerialName("boxImage") val boxImage: String?,
    @SerialName("envelopeId") val envelopeId: Int?,
    @SerialName("envelopeUrl") val envelopeUrl: String?,
    @SerialName("gift") val gift: Gift?,
    @SerialName("letterContent") val letterContent: String?,
    @SerialName("name") val name: String?,
    @SerialName("photos") val photo: Photo?,
    @SerialName("receiverName") val receiverName: String?,
    @SerialName("senderName") val senderName: String?,
    @SerialName("stickers") val stickers: List<Stickers>,
    @SerialName("youtubeUrl") val youtubeUrl: String?,
    @SerialName("lottieAnimation") val lottieAnimation: String?,
) {
    fun boxAllReady(): Boolean {
        println("LOGEE boxId $boxId boxImage $boxImage envelopeId $envelopeId envelopeUrl $envelopeUrl gift $gift letterContent $letterContent name $name photo $photo receiverName $receiverName senderName $senderName stickers $stickers youtubeUrl $youtubeUrl lottieAnimation $lottieAnimation")
        return boxId != null &&
                envelopeId != null &&
                envelopeUrl != null &&
                letterContent != null &&
                name != null &&
                photo != null &&
                receiverName != null &&
                senderName != null &&
                stickers.all { it.id != null && it.location != null && it.imageUri != null } &&
                youtubeUrl != null &&
                lottieAnimation != null
    }
}