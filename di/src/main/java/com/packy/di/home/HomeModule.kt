package com.packy.di.home

import com.packy.data.remote.home.HomeService
import com.packy.data.repository.home.HomeRepositoryImp
import com.packy.data.usecase.home.GetHomeBoxPaginationUseCaseImp
import com.packy.data.usecase.home.GetHomeBoxUseCaseImp
import com.packy.data.usecase.home.GetLazyBoxUseCaseImp
import com.packy.data.usecase.home.GetNoticeGiftBoxUseCaseImp
import com.packy.data.usecase.home.GetNoticesUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.home.GetHomeBoxPaginationUseCase
import com.packy.domain.usecase.home.GetHomeBoxUseCase
import com.packy.domain.usecase.home.GetLazyBoxUseCase
import com.packy.domain.usecase.home.GetNoticeGiftBoxUseCase
import com.packy.domain.usecase.home.GetNoticesUseCase
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

    @Binds
    abstract fun bindGetHomeBoxPaginationUseCase(useCase: GetHomeBoxPaginationUseCaseImp): GetHomeBoxPaginationUseCase

    @Binds
    abstract fun bindGetLazyBoxUseCase(useCase: GetLazyBoxUseCaseImp): GetLazyBoxUseCase

    @Binds
    abstract fun bindGetNoticeGiftBoxUseCase(useCase: GetNoticeGiftBoxUseCaseImp): GetNoticeGiftBoxUseCase

    @Binds
    abstract fun bindGetNoticeUseCase(useCase: GetNoticesUseCaseImp): GetNoticesUseCase
}