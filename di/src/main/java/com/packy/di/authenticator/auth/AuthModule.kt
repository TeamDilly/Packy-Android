package com.packy.di.authenticator.auth

import com.packy.data.usecase.auth.LogoutUseCaseImp
import com.packy.domain.usecase.auth.LogoutUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindLogoutUseCase(useCase: LogoutUseCaseImp): LogoutUseCase
}