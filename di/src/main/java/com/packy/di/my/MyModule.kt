package com.packy.di.my

import com.packy.data.remote.my.MyProfileService
import com.packy.data.repository.my.MyInfoRepositoryImp
import com.packy.data.repository.photo.PhotoRepositoryImp
import com.packy.data.usecase.my.GetMyNickNameUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.my.MyInfoRepository
import com.packy.domain.repository.photo.PhotoRepository
import com.packy.domain.usecase.my.GetMyNickNameUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyModule {
    @Provides
    @Singleton
    fun providerMyProfileService(
        @Packy httpClient: HttpClient
    ): MyProfileService =
        MyProfileService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MyInfoModule {

    @Binds
    abstract fun bindMyInfoRepository(repository: MyInfoRepositoryImp): MyInfoRepository

    @Binds
    abstract fun bindGetMyNickNameUseCase(useCase: GetMyNickNameUseCaseImp): GetMyNickNameUseCase

}
