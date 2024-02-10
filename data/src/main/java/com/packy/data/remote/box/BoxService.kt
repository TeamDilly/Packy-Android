package com.packy.data.remote.box

import com.packy.data.model.createbox.BoxDesignDto
import com.packy.data.model.createbox.CreateBoxDto
import com.packy.data.model.createbox.box.CreateBoxRequest
import com.packy.data.model.getbox.GiftBoxDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class BoxService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getBoxDesign(): Resource<List<BoxDesignDto>> = safeRequest {
        httpClient.get("api/v1/admin/design/boxes")
    }

    suspend fun createBox(
        createBoxRequest: CreateBoxRequest
    ): Resource<CreateBoxDto> = safeRequest {
        httpClient.post("/api/v1/giftboxes") {
            contentType(ContentType.Application.Json)
            setBody(createBoxRequest)
        }
    }

    suspend fun getGifBox(
        giftBoxId: String
    ): Resource<GiftBoxDto> = safeRequest {
        httpClient.get("/api/v1/giftboxes/${giftBoxId}")
    }
}