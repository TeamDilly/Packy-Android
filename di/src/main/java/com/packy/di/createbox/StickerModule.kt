package com.packy.di.createbox

import com.packy.data.remote.createbox.StickerService
import com.packy.data.repository.createbox.StickerRepositoryImp
import com.packy.data.usecase.createbox.GetStickerUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.createbox.StickerRepository
import com.packy.domain.usecase.createbox.GetStickerUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StickerServiceModule {
    @Provides
    @Singleton
    fun provideStickerService(
        @Packy httpClient: HttpClient
    ): StickerService = StickerService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StickerRepositoryModule {

    @Binds
    abstract fun bindStickerRepository(repository: StickerRepositoryImp): StickerRepository

    @Binds
    abstract fun bindStickerUseCase(useCase: GetStickerUseCaseImp): GetStickerUseCase
}