package com.packy.di.createbox

import com.packy.data.remote.createbox.LatterService
import com.packy.data.repository.createbox.LatterRepositoryImp
import com.packy.data.usecase.createbox.GetLatterSenderReceiverImp
import com.packy.data.usecase.createbox.LatterUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.createbox.LatterRepository
import com.packy.domain.usecase.createbox.GetLatterSenderReceiver
import com.packy.domain.usecase.createbox.LatterUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LatterServiceModule {
    @Provides
    @Singleton
    fun provideLatterService(
        @Packy httpClient: HttpClient
    ): LatterService = LatterService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LatterRepositoryModule {

    @Binds
    abstract fun bindLatterRepository(latterRepository: LatterRepositoryImp): LatterRepository

    @Binds
    abstract fun bindLatterUseCase(latterUseCase: LatterUseCaseImp): LatterUseCase

    @Binds
    abstract fun bindGetLatterSenderReceiver(useCase: GetLatterSenderReceiverImp): GetLatterSenderReceiver
}