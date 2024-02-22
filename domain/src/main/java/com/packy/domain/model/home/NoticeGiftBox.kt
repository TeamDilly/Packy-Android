package com.packy.domain.model.home

import kotlinx.serialization.Serializable

@Serializable
data class NoticeGiftBox(
    val giftBoxId: Long,
    val title: String,
    val sender: String,
    val boxImage: String,
)
