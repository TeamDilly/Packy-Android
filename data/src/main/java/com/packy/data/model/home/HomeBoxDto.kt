package com.packy.data.model.home

import com.packy.domain.model.home.HomeBox
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeBoxDto(
    @SerialName("boxFull") val boxFull: String,
    @SerialName("giftBoxDate") val giftBoxDate: String,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("sender") val sender: String
)

fun HomeBoxDto.toEntity(): HomeBox = HomeBox(
    boxId = id,
    boxImageUrl = boxFull,
    sender = sender,
    title = name,
    giftBoxDate = giftBoxDate
)