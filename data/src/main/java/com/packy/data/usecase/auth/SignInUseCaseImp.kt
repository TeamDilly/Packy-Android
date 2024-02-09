package com.packy.data.usecase.auth

import com.packy.domain.model.auth.SignIn
import com.packy.domain.repository.auth.SignInRepository
import com.packy.domain.usecase.auth.SignInUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCaseImp @Inject constructor(
    private val signInRepository: SignInRepository
) : SignInUseCase {
    override suspend fun signIn(token: String, nickname: String?): Flow<Resource<SignIn>> =
        signInRepository.signIn(token = token, nickname =nickname)
}