package com.packy.data.repository.auth

import com.packy.data.local.AccountPrefManager
import com.packy.data.model.auth.SignUpDto
import com.packy.data.model.auth.SignUpRequest
import com.packy.data.remote.auth.SignUpService
import com.packy.domain.model.auth.SignUp
import com.packy.domain.repository.SignUpRepository
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpRepositoryImp @Inject constructor(
    private val accountPref: AccountPrefManager,
    private val api: SignUpService
) : SignUpRepository {
    override suspend fun getUserSignUpInfo(): Flow<SignUp> =
        accountPref.signUp.getData()


    override suspend fun setUserSignUpInfo(signUp: SignUp) {
        accountPref.signUp.putData(signUp)
    }

    override suspend fun signUp(signUp: SignUp): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            api.signUp(
                SignUpRequest(
                    nickname = signUp.nickname,
                    profileImg = signUp.profileImg,
                    provider = signUp.provider,
                    marketingAgreement = signUp.marketingAgreement,
                    pushNotification = signUp.pushNotification
                ),
                token = signUp.token
            )
        }
    }
}