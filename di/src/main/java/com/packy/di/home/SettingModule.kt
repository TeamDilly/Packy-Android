package com.packy.di.home

import com.packy.data.remote.home.SettingService
import com.packy.data.repository.home.SettingRepositoryImp
import com.packy.data.usecase.home.GetMyProfileUseCaseImp
import com.packy.data.usecase.home.GetSettingUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.home.SettingRepository
import com.packy.domain.usecase.home.GetMyProfileUseCase
import com.packy.domain.usecase.home.GetSettingUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingServiceModule {
    @Provides
    @Singleton
    fun provideSettingService(
        @Packy httpClient: HttpClient
    ): SettingService =
        SettingService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingRepositoryModule {

    @Binds
    abstract fun bindSettingRepository(repository: SettingRepositoryImp): SettingRepository

    @Binds
    abstract fun bindSettingUseCase(useCase: GetSettingUseCaseImp): GetSettingUseCase

    @Binds
    abstract fun bindMyProfileUseCase(useCase: GetMyProfileUseCaseImp): GetMyProfileUseCase

}
