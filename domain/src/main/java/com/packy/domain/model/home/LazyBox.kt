package com.packy.domain.model.home

import kotlinx.serialization.Serializable

@Serializable
data class LazyBox(
    val boxId: Long,
    val receiverName: String,
    val createdAt: String,
    val boxImageUrl: String,
    val boxTitle: String
)
