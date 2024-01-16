package com.packy.di.authenticator.signup

import com.packy.data.remote.auth.SignUpService
import com.packy.data.repository.auth.SignUpRepositoryImp
import com.packy.data.usecase.auth.SignUpUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.SignUpRepository
import com.packy.domain.usecase.auth.SignUpUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SignUpServiceModule {
    @Provides
    fun providerSignUpService(
        @Packy retrofit: Retrofit
    ): SignUpService =
        retrofit.create(SignUpService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SignRepositoryModule {

    @Binds
    abstract fun bindSignRepository(signUpRepository: SignUpRepositoryImp): SignUpRepository

    @Binds
    abstract fun bindSignUseCase(signUpUseCase: SignUpUseCaseImp): SignUpUseCase
}