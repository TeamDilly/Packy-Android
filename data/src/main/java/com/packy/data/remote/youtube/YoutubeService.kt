package com.packy.data.remote.youtube

import com.packy.data.model.youtube.ValidationYoutubeUrlDto
import com.packy.data.model.youtube.YoutubeInfoDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class YoutubeService @Inject constructor(
    private val httpClient: HttpClient,
    private val packyHttpClient: HttpClient
) {
    suspend fun getYoutubeInfo(
        url: String,
    ): YoutubeInfoDto = httpClient.get {
        url("oembed")
        parameter("url", url)
        parameter("format", "json")
    }.body()

    suspend fun checkValidationYoutubeUrl(
        url: String
    ): Resource<ValidationYoutubeUrlDto> = safeRequest{
        packyHttpClient.get("/api/v1/admin/youtube"){
            parameter("url", url)
        }
    }

}