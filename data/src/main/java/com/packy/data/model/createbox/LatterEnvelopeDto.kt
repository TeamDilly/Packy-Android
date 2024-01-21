package com.packy.data.model.createbox

import com.packy.domain.model.createbox.LatterEnvelope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatterEnvelopeDto(
    @SerialName("envelope") val envelope: String,
    @SerialName("id") val id: Int,
    @SerialName("letterPaper") val letterPaper: String,
)

fun LatterEnvelopeDto.toEntity() = LatterEnvelope(
    envelope, id, letterPaper
)

fun List<LatterEnvelopeDto>.toEntity(): List<LatterEnvelope> = this.map { it.toEntity() }