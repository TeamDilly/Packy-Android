package com.packy.data.model.createbox

import com.packy.domain.model.createbox.LatterEnvelope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatterEnvelopeDto(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
)

fun LatterEnvelopeDto.toEntity() = LatterEnvelope(
    imgUrl = imgUrl,
    id = id,
    sequence = sequence,
)

fun List<LatterEnvelopeDto>.toEntity(): List<LatterEnvelope> = this.map { it.toEntity() }