package com.packy.di.authenticator.auth

import com.packy.data.remote.auth.WithdrawService
import com.packy.data.repository.auth.WithdrawRepositoryImp
import com.packy.data.usecase.auth.WithdrawUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.auth.WithdrawRepository
import com.packy.domain.usecase.auth.WithdrawUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WithdrawServiceModule {
    @Provides
    @Singleton
    fun provideWithdrawService(
        @Packy httpClient: HttpClient
    ): WithdrawService = WithdrawService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class WithdrawRepositoryModule {

    @Binds
    abstract fun bindWithdrawRepository(repository: WithdrawRepositoryImp): WithdrawRepository

    @Binds
    abstract fun bindWithdrawUseCase(useCase: WithdrawUseCaseImp): WithdrawUseCase
}