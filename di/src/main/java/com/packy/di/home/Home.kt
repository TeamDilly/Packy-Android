package com.packy.di.home

import com.packy.data.remote.home.HomeService
import com.packy.data.repository.home.HomeRepositoryImp
import com.packy.data.usecase.home.GetHomeBoxUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeServiceModule {
    @Provides
    @Singleton
    fun provideHomeService(
        @Packy httpClient: HttpClient
    ): HomeService = HomeService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeRepositoryModule {

    @Binds
    abstract fun bindHomeRepository(repository: HomeRepositoryImp): HomeRepository

    @Binds
    abstract fun bindHomeUseCase(useCase: GetHomeBoxUseCaseImp): GetHomeBoxUseCase
}