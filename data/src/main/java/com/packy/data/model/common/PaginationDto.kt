package com.packy.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto<T>(
    val data: List<T>,
    val first: Boolean,
    val last: Boolean
)
