package com.packy.data.model.home

import com.packy.domain.model.home.NoticeGiftBox
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticeGiftBoxDto(
    @SerialName("giftBoxId") val giftBoxId: Long,
    @SerialName("name") val name: String,
    @SerialName("normalImgUrl") val normalImgUrl: String,
    @SerialName("senderName") val senderName: String
){
    fun toEntity(): NoticeGiftBox = NoticeGiftBox(
        giftBoxId = giftBoxId,
        title = name,
        boxImage = normalImgUrl,
        sender =  senderName
    )
}