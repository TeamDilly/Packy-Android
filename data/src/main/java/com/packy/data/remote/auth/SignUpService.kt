package com.packy.data.remote.auth

import com.packy.data.model.auth.SignInDto
import com.packy.data.model.auth.SignUpDto
import com.packy.data.model.auth.SignUpRequest
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class SignUpService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun signUp(
        signUpRequest: SignUpRequest,
        token: String
    ): Resource<SignUpDto> = safeRequest {
        httpClient.post(urlString = "api/v1/auth/sign-up") {
            header(
                "Authorization",
                token
            )
            setBody(signUpRequest)
        }
    }
}