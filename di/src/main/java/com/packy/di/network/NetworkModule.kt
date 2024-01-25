package com.packy.di.network

import android.util.Log
import com.packy.account.AccountManagerHelper
import com.packy.di.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
}