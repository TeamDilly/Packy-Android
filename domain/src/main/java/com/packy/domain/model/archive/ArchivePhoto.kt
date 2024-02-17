package com.packy.domain.model.archive

import kotlinx.serialization.Serializable

@Serializable
data class ArchivePhoto(
    val photoUrl: String,
    val photoContentText: String
)
