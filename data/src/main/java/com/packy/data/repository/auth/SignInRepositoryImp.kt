package com.packy.data.repository.auth

import com.packy.account.AccountManagerHelper
import com.packy.data.model.auth.SignInDto
import com.packy.data.model.auth.toEntity
import com.packy.data.remote.auth.SignInService
import com.packy.domain.model.auth.SignIn
import com.packy.domain.repository.auth.SignInRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInRepositoryImp @Inject constructor(
    private val api: SignInService,
    private val accountManagerHelper: AccountManagerHelper
) : SignInRepository {
    override suspend fun signIn(token: String): Flow<Resource<SignIn>> {
        return flow {
            emit(Resource.Loading())
            val signDto = api.signIn(token = token)
            val signIn = signDto.map { it.toEntity() }

            if (signDto.data?.status == SignIn.AuthStatus.REGISTERED.name) {
                val tokenInfo = signDto.data?.tokenInfo
                tokenInfo?.let { token ->
                    token.accessToken?.let {
                        accountManagerHelper.setAuthToken(
                            email = "Packy",
                            token = it,
                            refreshToken = token.refreshToken
                        )
                    }
                }
            }

            emit(signIn)
        }
    }
}