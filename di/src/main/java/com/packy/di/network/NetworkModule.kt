package com.packy.di.network

import android.util.Log
import com.packy.di.common.NetworkConstant
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.packy.account.AccountManagerHelper
import com.packy.di.BuildConfig
import com.packy.lib.network.PackyJsonAdapter
import com.packy.lib.network.ResourceAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
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
object NetworkModule {

    @Provides
    @Singleton
    @Default
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("Ktor", message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    @Packy
    fun provideKtorClient(
        @Default httpClient: HttpClient,
        accountManagerHelper: AccountManagerHelper,
    ): HttpClient {
        return httpClient.config {
            install(DefaultRequest) {
                url(BuildConfig.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                accountManagerHelper.getAutToken()?.let {
                    header("Authorization", it)
                }
            }
        }
    }

    @Provides
    @Singleton
    @Youtube
    fun provideYoutubeKtorClient(
        @Default httpClient: HttpClient,
        accountManagerHelper: AccountManagerHelper,
    ): HttpClient {
        return httpClient.config {
            install(DefaultRequest) {
                url("https://www.youtube.com/")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                accountManagerHelper.getAutToken()?.let {
                    header("Authorization", it)
                }
            }
        }
    }

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