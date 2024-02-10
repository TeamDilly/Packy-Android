package com.packy.data.remote.auth

import com.packy.data.model.auth.SignInDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import javax.inject.Inject

class SignInService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun signIn(token: String): Resource<SignInDto> = safeRequest {
        httpClient.get(urlString = "api/v1/auth/sign-in/kakao") {
            header("Authorization", token)
        }
    }
}


