package com.packy.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto<T>(
    val content: List<T>,
    val first: Boolean,
    val last: Boolean
)
