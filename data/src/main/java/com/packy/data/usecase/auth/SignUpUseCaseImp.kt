package com.packy.data.usecase.auth

import com.packy.data.local.AccountPrefManager
import com.packy.domain.model.auth.SignUp
import com.packy.domain.repository.SignUpRepository
import com.packy.domain.usecase.auth.SignUpUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCaseImp @Inject constructor(
    private val signUpRepository: SignUpRepository
) : SignUpUseCase {
    override suspend fun getUserSignUpInfo(): Flow<SignUp?> = signUpRepository.getUserSignUpInfo()

    override suspend fun setUserSignUpInfo(signUp: SignUp) =
        signUpRepository.setUserSignUpInfo(signUp)

    override suspend fun signUp(signUp: SignUp): Flow<Resource<Unit>> =
        signUpRepository.signUp(signUp)

}