package com.packy.di.photo

import android.content.ContentResolver
import android.content.Context
import com.packy.data.remote.photo.PhotoService
import com.packy.data.repository.photo.PhotoRepositoryImp
import com.packy.data.usecase.photo.UploadImageUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.photo.PhotoRepository
import com.packy.domain.usecase.photo.UploadImageUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoServiceModule {
    @Provides
    @Singleton
    fun providePhotoService(
        @Packy httpClient: HttpClient,
        contentResolver: ContentResolver,
        @ApplicationContext context: Context
    ): PhotoService = PhotoService(
        httpClient,
        contentResolver,
        context
    )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoRepositoryModule {

    @Binds
    abstract fun bindPhotoRepository(repository: PhotoRepositoryImp): PhotoRepository

    @Binds
    abstract fun bindPhotoUseCase(useCase: UploadImageUseCaseImp): UploadImageUseCase
}