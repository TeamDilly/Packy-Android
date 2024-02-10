package com.packy.data.remote.my

import com.packy.data.model.my.MyProfileDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class MyProfileService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getMyProfile(): Resource<MyProfileDto> = safeRequest {
        httpClient.get("/api/v1/my-page/profile")
    }
}