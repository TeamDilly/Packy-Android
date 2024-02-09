package com.packy.domain.repository.auth

import com.packy.domain.model.auth.SignIn
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SignInRepository {

    suspend fun signIn(token: String, nickname: String?): Flow<Resource<SignIn>>
}