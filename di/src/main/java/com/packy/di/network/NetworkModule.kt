package com.packy.di.network

import android.util.Log
import com.packy.account.AccountManagerHelper
import com.packy.di.BuildConfig
import com.packy.di.authenticator.model.TokenInfo
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
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
    fun provideHttpClient(
        accountManagerHelper: AccountManagerHelper,
    ): HttpClient {
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
                        Log.i(
                            "Ktor",
                            message
                        )
                    }
                }
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    refreshTokens {
                        val refreshToken = accountManagerHelper.getRefreshToken()
                        refreshToken?.let {
                            val token = client.get(urlString = "/api/v1/auth/reissue") {
                                header(
                                    "Authorization",
                                    refreshToken
                                )
                            }.toResource<TokenInfo>()

                            if (token is Resource.Success) {
                                accountManagerHelper.setAuthToken(
                                    email = "Packy",
                                    token = token.data.accessToken,
                                    refreshToken = token.data.refreshToken
                                )

                                BearerTokens(
                                    accessToken =  token.data.accessToken,
                                    refreshToken = token.data.refreshToken
                                )
                            } else {
                                null
                            }
                        }
                    }
                }
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
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json
                )
                accountManagerHelper.getAutToken()?.let {
                    header(
                        "Authorization",
                        it
                    )
                }
            }
        }
    }

    @Provides
    @Singleton
    @Youtube
    fun provideYoutubeKtorClient(
        @Default httpClient: HttpClient,
    ): HttpClient {
        return httpClient.config {
            install(DefaultRequest) {
                url("https://www.youtube.com/")
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json
                )
            }
        }
    }
}