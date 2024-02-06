package com.packy.di.my

import com.packy.data.remote.my.MyProfileService
import com.packy.di.network.Packy
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