package com.packy.domain.usecase.auth

import com.packy.domain.model.auth.SignUp
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SignUpUseCase {
    suspend fun getUserSignUpInfo(): Flow<SignUp>
    suspend fun setUserSignUpInfo(signUp: SignUp): Unit
    suspend fun signUp(signUp: SignUp): Flow<Resource<Unit>>
}