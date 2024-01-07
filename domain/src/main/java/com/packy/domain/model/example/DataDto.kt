package com.packy.domain.model.example

import kotlinx.serialization.Serializable

@Serializable
data class DataDto(
    val avatar: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val last_name: String
)