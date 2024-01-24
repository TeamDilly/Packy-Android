package com.packy.data.model.createbox

import com.packy.domain.model.createbox.LetterEnvelope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterEnvelopeDto(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
)

fun LetterEnvelopeDto.toEntity() = LetterEnvelope(
    imgUrl = imgUrl,
    id = id,
    sequence = sequence,
)

fun List<LetterEnvelopeDto>.toEntity(): List<LetterEnvelope> = this.map { it.toEntity() }