package com.packy.domain.repository

import com.packy.lib.utils.Resource
import com.packy.domain.model.auth.SignUp
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {

    suspend fun getUserSignUpInfo(): Flow<SignUp?>
    suspend fun setUserSignUpInfo(signUp: SignUp): Unit
    suspend fun signUp(signUp: SignUp): Flow<Resource<Unit>>
}