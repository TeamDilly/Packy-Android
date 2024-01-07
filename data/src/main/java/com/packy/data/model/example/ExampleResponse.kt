package com.packy.data.model.example

import com.packy.domain.model.example.ExampleDataDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExampleResponse(
    @SerialName("data") val dataList: List<Data>,
    @SerialName("page") val page: Int,
    @SerialName("per_page") val per_page: Int,
    @SerialName("support") val support: Support,
    @SerialName("total") val total: Int,
    @SerialName("total_pages") val total_pages: Int
)

fun ExampleResponse.toDto() =
    ExampleDataDto(dataList.map { it.toDto() }, page, per_page, support.toDto(), total, total_pages)