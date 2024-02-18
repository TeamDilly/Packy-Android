package com.packy.di.archive

import com.packy.data.remote.archive.ArchiveService
import com.packy.data.repository.archive.ArchiveRepositoryImp
import com.packy.data.usecase.archive.GetArchiveGiftImp
import com.packy.data.usecase.archive.GetArchiveLetterImp
import com.packy.data.usecase.archive.GetArchiveMusicImp
import com.packy.data.usecase.archive.GetArchivePhotoImp
import com.packy.di.network.Packy
import com.packy.domain.repository.archive.ArchiveRepository
import com.packy.domain.usecase.archive.GetArchiveGift
import com.packy.domain.usecase.archive.GetArchiveLetter
import com.packy.domain.usecase.archive.GetArchiveMusic
import com.packy.domain.usecase.archive.GetArchivePhoto
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArchiveServiceModule {
    @Provides
    @Singleton
    fun provideArchiveService(
        @Packy httpClient: HttpClient
    ): ArchiveService = ArchiveService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ArchiveRepositoryModule {

    @Binds
    abstract fun bindArchiveRepository(repository: ArchiveRepositoryImp): ArchiveRepository

    @Binds
    abstract fun bindArchiveGiftUseCase(useCase: GetArchiveGiftImp): GetArchiveGift

    @Binds
    abstract fun bindArchiveLetterUseCase(useCase: GetArchiveLetterImp): GetArchiveLetter

    @Binds
    abstract fun bGetArchiveMusicUseCase(useCase: GetArchiveMusicImp): GetArchiveMusic

    @Binds
    abstract fun bGetArchivePhotoUseCase(useCase: GetArchivePhotoImp): GetArchivePhoto
}