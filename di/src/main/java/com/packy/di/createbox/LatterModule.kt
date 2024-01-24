package com.packy.di.createbox

import com.packy.data.remote.createbox.LetterService
import com.packy.data.repository.letter.LetterRepositoryImp
import com.packy.data.usecase.createbox.GetLetterSenderReceiverUseCaseImp
import com.packy.data.usecase.createbox.LetterUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.letter.LetterRepository
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import com.packy.domain.usecase.letter.LetterUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LetterServiceModule {
    @Provides
    @Singleton
    fun provideLetterService(
        @Packy httpClient: HttpClient
    ): LetterService = LetterService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LetterRepositoryModule {

    @Binds
    abstract fun bindLetterRepository(LetterRepository: LetterRepositoryImp): LetterRepository

    @Binds
    abstract fun bindLetterUseCase(LetterUseCase: LetterUseCaseImp): LetterUseCase

    @Binds
    abstract fun bindGetLetterSenderReceiver(useCase: GetLetterSenderReceiverUseCaseImp): GetLetterSenderReceiverUseCase
}