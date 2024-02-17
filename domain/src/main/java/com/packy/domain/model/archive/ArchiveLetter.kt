package com.packy.domain.model.archive

import kotlinx.serialization.Serializable

@Serializable
data class ArchiveLetter(
    val letterContent: String,
    val envelopeUrl: String,
    val borderColor: String,
    val borderOpacity: Int
)
