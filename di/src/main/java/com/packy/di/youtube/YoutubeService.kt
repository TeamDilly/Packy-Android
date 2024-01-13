package com.packy.di.youtube

import com.packy.data.remote.example.ExampleService
import com.packy.data.remote.youtube.YoutubeService
import com.packy.di.network.Youtube
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object YoutubeService {
    @Provides
    fun providerYoutubeService(
        @Youtube retrofit: Retrofit
    ): YoutubeService =
        retrofit.create(YoutubeService::class.java)
}