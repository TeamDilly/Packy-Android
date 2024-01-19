package com.packy.data.repository.auth

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
    private val api: SignInService
) : SignInRepository {
    override suspend fun signIn(token: String): Flow<Resource<SignIn>> {
        val signDto = api.signIn(token = token)
        return flow {
            // FIXME
        }
    }
}