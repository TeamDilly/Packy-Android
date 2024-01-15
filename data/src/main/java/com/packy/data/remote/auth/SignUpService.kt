package com.packy.data.remote.auth

import com.packy.data.model.auth.SignUpDto
import com.packy.data.model.auth.SignUpRequest
import com.packy.lib.utils.Resource
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/v1/auth/sign-up")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Resource<SignUpDto>
}