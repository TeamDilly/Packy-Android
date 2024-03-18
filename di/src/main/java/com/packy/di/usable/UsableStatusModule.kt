package com.packy.di.usable

import com.packy.data.remote.usable.UsableService
import com.packy.di.network.Packy
import com.packy.domain.repository.usable.UsableStatusRepository
import com.packy.domain.repository.usable.UsableStatusRepositoryImp
import com.packy.domain.usecase.usable.GetUsableUseCase
import com.packy.domain.usecase.usable.GetUsableUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsableStatusServiceModule {
    @Provides
    @Singleton
    fun provideUsableStatusService(@Packy httpClient: HttpClient): UsableService =
        UsableService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UsableStatusRepositoryModule {

    @Binds
    abstract fun bindUsableStatusRepository(repository: UsableStatusRepositoryImp): UsableStatusRepository

    @Binds
    abstract fun bindUsableStatusUseCase(useCase: GetUsableUseCaseImp): GetUsableUseCase
}