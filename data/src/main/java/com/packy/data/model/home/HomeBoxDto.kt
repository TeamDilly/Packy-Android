package com.packy.data.model.home

import com.packy.domain.model.home.BoxType
import com.packy.domain.model.home.HomeBox
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeBoxDto(
    @SerialName("boxNormal") val boxNormal: String,
    @SerialName("giftBoxDate") val giftBoxDate: String,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("sender") val sender: String,
    @SerialName("type") val type: String,
    @SerialName("receiver") val receiver: String,
)

fun HomeBoxDto.toEntity(): HomeBox = HomeBox(
    boxId = id,
    boxImageUrl = boxNormal,
    sender = sender,
    title = name,
    giftBoxDate = giftBoxDate,
    type = if (type == "sent") BoxType.SENT else BoxType.RECEIVED,
    receiver = receiver
)