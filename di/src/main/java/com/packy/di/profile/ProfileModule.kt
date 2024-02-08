package com.packy.di.profile

import com.packy.data.remote.profile.ProfileService
import com.packy.data.repository.profile.ProfileRepositoryImp
import com.packy.data.usecase.profile.GetProfilesUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.profile.ProfileRepository
import com.packy.domain.usecase.profile.GetProfilesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileServiceModule {
    @Provides
    @Singleton
    fun provideProfileService(
        @Packy httpClient: HttpClient): ProfileService = ProfileService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {

    @Binds
    abstract fun bindProfileRepository(repository: ProfileRepositoryImp): ProfileRepository

    @Binds
    abstract fun bindProfileUseCase(useCase: GetProfilesUseCaseImp): GetProfilesUseCase
}