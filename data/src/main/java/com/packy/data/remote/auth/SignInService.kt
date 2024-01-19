package com.packy.data.remote.auth

import com.packy.data.model.auth.SignInDto
import com.packy.lib.utils.Resource
import retrofit2.http.GET
import retrofit2.http.Header

interface SignInService {

    @GET("api/v1/auth/sign-in/kakao")
    suspend fun signIn(
        @Header("Authorization") token: String
    ): Resource<SignInDto>
}