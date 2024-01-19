package com.packy.di.authenticator.signup

import com.packy.data.remote.auth.SignInService
import com.packy.data.repository.auth.SignInRepositoryImp
import com.packy.data.usecase.auth.SignInUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.auth.SignInRepository
import com.packy.domain.usecase.auth.SignInUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SignInServiceModule {
    @Provides
    fun providerSignInService(
        @Packy retrofit: Retrofit
    ): SignInService =
        retrofit.create(SignInService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SignInRepositoryModule {

    @Binds
    abstract fun bindSignInRepository(signInRepository: SignInRepositoryImp): SignInRepository

    @Binds
    abstract fun bindSignInUseCase(signInUseCase: SignInUseCaseImp): SignInUseCase
}