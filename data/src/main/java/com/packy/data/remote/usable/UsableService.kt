package com.packy.data.remote.usable

import com.packy.domain.model.usable.UsableDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class UsableService @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun getUsable(
        version: String
    ): Resource<UsableDto> = safeRequest {
        httpClient.get("/api/v1/member/status") {
            parameter(
                "app-version",
                version
            )
        }
    }
}