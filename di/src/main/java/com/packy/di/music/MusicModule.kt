package com.packy.di.music

import com.packy.data.remote.music.MusicService
import com.packy.data.repository.music.MusicRepositoryImp
import com.packy.data.usecase.music.SuggestionMusicUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.music.MusicRepository
import com.packy.domain.usecase.music.SuggestionMusicUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicServiceModule {
    @Provides
    @Singleton
    fun providerMusicService(
        @Packy httpClient: HttpClient
    ): MusicService =
        MusicService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MusicRepositoryModule {
    @Binds
    abstract fun bindMusicRepository(signInRepository: MusicRepositoryImp): MusicRepository

    @Binds
    abstract fun bindMusicUseCase(signInUseCase: SuggestionMusicUseCaseImp): SuggestionMusicUseCase
}