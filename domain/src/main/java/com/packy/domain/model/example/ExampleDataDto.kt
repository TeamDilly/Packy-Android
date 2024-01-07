package com.packy.domain.model.example

import kotlinx.serialization.Serializable

@Serializable
data class ExampleDataDto(
    val dataList: List<DataDto>,
    val page: Int,
    val per_page: Int,
    val support: SupportDto,
    val total: Int,
    val total_pages: Int
)