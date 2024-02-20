package com.packy.di.profile

import com.packy.data.remote.profile.ProfileUpdateService
import com.packy.data.repository.profile.ProfileUpdateRepositoryImp
import com.packy.data.usecase.profile.UpdateNickNameUseCaseImp
import com.packy.data.usecase.profile.UpdateProfileUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.profile.ProfileUpdateRepository
import com.packy.domain.usecase.profile.UpdateNickNameUseCase
import com.packy.domain.usecase.profile.UpdateProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileUpdateServiceModule {
    @Provides
    @Singleton
    fun provideProfileUpdateService(@Packy httpClient: HttpClient): ProfileUpdateService =
        ProfileUpdateService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileUpdateRepositoryModule {

    @Binds
    abstract fun bindProfileUpdateRepository(repository: ProfileUpdateRepositoryImp): ProfileUpdateRepository

    @Binds
    abstract fun bindProfileUpdateNicknameUseCase(useCase: UpdateNickNameUseCaseImp): UpdateNickNameUseCase
    @Binds
    abstract fun bindProfileUpdateProfileImageUseCase(useCase: UpdateProfileUseCaseImp): UpdateProfileUseCase

}