package com.packy.domain.model.usable

import kotlinx.serialization.Serializable

@Serializable
enum class UsableStatus{
    NEED_UPDATE,
    INVALID_STATUS
}
