package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchiveLetter
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveLetterDto(
    val envelope: ArchiveEnvelopeDto,
    val id: Int,
    val letterContent: String
){
    fun toEntity(): ArchiveLetter = ArchiveLetter(
        letterContent = letterContent,
        envelopeUrl = envelope.imgUrl,
        borderColor = envelope.borderColorCode,
        borderOpacity = envelope.opacity
    )
}

@Serializable
data class ArchiveEnvelopeDto(
    val borderColorCode: String,
    val imgUrl: String,
    val opacity: Int
)

