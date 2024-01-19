package com.packy.di.network

import com.packy.di.common.NetworkConstant
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.packy.di.BuildConfig
import com.packy.lib.network.PackyJsonAdapter
import com.packy.lib.network.ResourceAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(NetworkConstant.connectTimeout, TimeUnit.SECONDS)
        .writeTimeout(NetworkConstant.writeTiemout, TimeUnit.SECONDS)
        .readTimeout(NetworkConstant.readTiemout, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }
        .build()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json,
    ): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    @Youtube
    fun providerYoutubeRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.youtube.com/")
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Packy
    fun providerPackyRetorfit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(PackyJsonAdapter("application/json".toMediaType()))
            .addCallAdapterFactory(ResourceAdapterFactory())
            .client(okHttpClient)
            .build()
    }
}