package com.packy.di.youtube

import com.packy.data.repository.youtube.YoutubeRepositoryImp
import com.packy.domain.repository.youtube.YoutubeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class YoutubeModule {

    @Binds
    abstract fun bindYoutubeRepository(youtubeRepository: YoutubeRepositoryImp): YoutubeRepository
}