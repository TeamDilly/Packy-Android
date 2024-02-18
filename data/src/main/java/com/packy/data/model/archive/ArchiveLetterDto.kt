package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchiveLetter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveLetterDto(
    @SerialName("envelope") val envelope: ArchiveEnvelopeDto,
    @SerialName("id") val id: Int,
    @SerialName("letterContent") val letterContent: String
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
    @SerialName("borderColorCode") val borderColorCode: String,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("opacity") val opacity: Int
)

