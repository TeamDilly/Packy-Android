package com.packy.data.model.createbox

import com.packy.domain.model.createbox.LetterEnvelope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterEnvelopeDto(
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("envelope") val envelope: LetterDesignDto,
    @SerialName("letter") val letter: LetterDesignDto
)

fun LetterEnvelopeDto.toEntity() = LetterEnvelope(
    imgUrl = imgUrl,
    id = id,
    sequence = sequence,
    envelope = envelope.toEntity(),
    letter = letter.toEntity()
)

fun List<LetterEnvelopeDto>.toEntity(): List<LetterEnvelope> = this.map { it.toEntity() }