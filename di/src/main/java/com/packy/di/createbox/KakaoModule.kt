package com.packy.di.createbox

import android.content.Context
import com.packy.common.kakaoshare.KakaoShare
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KakaoModule {
    @Singleton
    @Provides
    fun providerKakaoShear(
    ): KakaoShare = KakaoShare()
}