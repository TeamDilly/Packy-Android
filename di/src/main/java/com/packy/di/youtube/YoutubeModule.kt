package com.packy.di.youtube

import com.packy.data.remote.youtube.YoutubeService
import com.packy.data.repository.youtube.YoutubeRepositoryImp
import com.packy.data.usecase.youtube.ValidationYoutubeUrlUseCaseImp
import com.packy.data.usecase.youtube.YoutubeUseCaseImp
import com.packy.di.network.Packy
import com.packy.di.network.Youtube
import com.packy.domain.repository.youtube.YoutubeRepository
import com.packy.domain.usecase.youtube.ValidationYoutubeUrlUseCase
import com.packy.domain.usecase.youtube.YoutubeUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YoutubeServiceModule {
    @Provides
    @Singleton
    fun provideYoutubeService(
        @Youtube httpClient: HttpClient,
        @Packy packyHttpClient: HttpClient
    ): YoutubeService = YoutubeService(
        httpClient,
        packyHttpClient
    )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class YoutubeRepositoryModule {

    @Binds
    abstract fun bindYoutubeRepository(youtubeRepository: YoutubeRepositoryImp): YoutubeRepository

    @Binds
    abstract fun bindYoutubeUseCase(youtubeUseCase: YoutubeUseCaseImp): YoutubeUseCase

    @Binds
    abstract  fun bindValidationYoutubeUrlUseCase(useCase: ValidationYoutubeUrlUseCaseImp): ValidationYoutubeUrlUseCase
}