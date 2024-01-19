package com.packy.domain.usecase.auth

import com.packy.domain.model.auth.SignIn
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SignInUseCase {
    suspend fun signIn(token: String): Flow<Resource<SignIn>>
}