package com.packy.domain.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterSenderReceiver(
    @SerialName("sender") val sender: String,
    @SerialName("receiver") val receiver: String
)
