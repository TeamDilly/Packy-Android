package com.packy.domain.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeBox(
    @SerialName("boxId") val boxId: Long,
    @SerialName("boxImageUrl") val boxImageUrl: String,
    @SerialName("sender") val sender: String,
    @SerialName("title") val title: String,
    @SerialName("giftBoxDate") val giftBoxDate: String,
    @SerialName("type") val type: BoxType,
    @SerialName("receiver") val receiver: String,
){
    val displayName = when(type){
        BoxType.SENT -> receiver
        BoxType.RECEIVED -> sender
    }
}