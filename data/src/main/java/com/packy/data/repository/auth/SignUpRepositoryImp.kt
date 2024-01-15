package com.packy.data.repository.auth

import com.packy.data.local.AccountPrefManager
import com.packy.domain.model.auth.SignUp
import com.packy.domain.repository.SignUpRepository
import com.packy.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpRepositoryImp @Inject constructor(
    private val accountPref: AccountPrefManager
) : SignUpRepository {
    override suspend fun getUserSignUpInfo(): Flow<SignUp?> =
        accountPref.signUp.getData()


    override suspend fun setUserSignUpInfo(signUp: SignUp) {
        accountPref.signUp.putData(signUp)
    }

    override suspend fun signUp(signUp: SignUp): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
        }
    }
}