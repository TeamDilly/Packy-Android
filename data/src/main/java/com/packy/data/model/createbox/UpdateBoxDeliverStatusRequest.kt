package com.packy.data.model.createbox

import com.packy.domain.model.box.BoxDeliverStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateBoxDeliverStatusRequest(
    @SerialName("deliverStatus") val deliverStatus: BoxDeliverStatus
)