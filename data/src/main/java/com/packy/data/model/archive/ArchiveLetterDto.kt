package com.packy.data.model.archive

import kotlinx.serialization.Serializable

@Serializable
data class ArchiveLetterDto(
    val envelope: ArchiveEnvelopeDto,
    val id: Int,
    val letterContent: String
)

@Serializable
data class ArchiveEnvelopeDto(
    val borderColorCode: String,
    val imgUrl: String,
    val opacity: Int
)