package com.packy.domain.repository

import com.packy.domain.model.auth.SignUp
import com.packy.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    suspend fun getUserSignUpInfo(): Flow<SignUp?>
    suspend fun setUserSignUpInfo(signUp: SignUp): Unit
    suspend fun signUp(signUp: SignUp): Flow<Resource<Unit>>
}