package com.packy.data.remote.auth

import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import javax.inject.Inject

class WithdrawService@Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun withdraw(): Resource<String> = safeRequest {
        httpClient.delete("/api/v1/auth/withdraw")
    }
}