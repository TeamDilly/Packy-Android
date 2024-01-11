package com.packy.di.authenticator

import android.content.Context
import com.packy.common.authenticator.KakaoLoginController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KakaoLoginControllerModule {

    @Singleton
    @Provides
    fun providerAccountManager(
        @ApplicationContext appContext: Context
    ): KakaoLoginController = KakaoLoginController(appContext)
}