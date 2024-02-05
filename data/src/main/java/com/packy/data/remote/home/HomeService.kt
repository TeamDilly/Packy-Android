package com.packy.data.remote.home

import com.packy.data.model.home.HomeBoxContentDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.sql.Timestamp
import javax.inject.Inject

class HomeService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getHomoBoxes(
        timestamp: String?,
        type: String,
        size: Int = 6
    ): Resource<HomeBoxContentDto> =
        httpClient.get(urlString = "/api/v1/giftboxes") {
            if (timestamp != null) {
                parameter(
                    "timestamp",
                    timestamp
                )
            }
            parameter(
                "type",
                type
            )

            parameter(
                "size",
                size
            )
        }.toResource()
}