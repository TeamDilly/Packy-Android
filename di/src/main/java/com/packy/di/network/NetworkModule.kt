package com.packy.di.network

import android.content.Context
import android.content.Intent
import android.util.Log
import com.packy.account.AccountManagerHelper
import com.packy.data.local.AccountPrefManager
import com.packy.di.BuildConfig
import com.packy.di.authenticator.model.RefreshTokenRequest
import com.packy.di.authenticator.model.TokenInfo
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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
        accountPrefManager: AccountPrefManager,
    ): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                if(BuildConfig.DEBUG){
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
            }
            // TODO : token 만료 시 정책 구분 및 정의
//            HttpResponseValidator {
//                handleResponseExceptionWithRequest { exception, request ->
//                    val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
//                    val exceptionResponse = clientException.response
//                    if(exceptionResponse.status.value == 404) {
//                        accountManagerHelper.removeAuthToken()
//                        accountPrefManager.clearAll()
//                    }
//                }
//            }
            install(Auth) {
                bearer {
                    refreshTokens {
                        val accessToken = accountManagerHelper.getAutToken()
                        val refreshToken = accountManagerHelper.getRefreshToken()
                        if (accessToken != null && refreshToken != null) {
                            val token = client.post(urlString = "/api/v1/auth/reissue") {
                                setBody(
                                    RefreshTokenRequest(
                                        accessToken = accessToken,
                                        refreshToken = refreshToken
                                    )
                                )
                            }.toResource<TokenInfo>()

                            if (token is Resource.Success) {
                                accountManagerHelper.setRefreshToken(
                                    token = token.data.accessToken,
                                    refreshToken = token.data.refreshToken
                                )

                                BearerTokens(
                                    accessToken = token.data.accessToken,
                                    refreshToken = token.data.refreshToken
                                )
                            } else {
                                null
                            }
                        } else {
                            null
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