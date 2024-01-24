package com.packy.data.model.createbox

import com.packy.domain.model.createbox.LetterSenderReceiver
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LetterSenderReceiverDto(
    @SerialName("sender") val sender: String,
    @SerialName("receiver") val receiver: String
)

fun LetterSenderReceiverDto.toEntity(): LetterSenderReceiver = LetterSenderReceiver(
    sender = sender,
    receiver = receiver
)