package com.packy.data.remote.box

import com.packy.data.model.createbox.BoxDesignDto
import com.packy.data.model.createbox.CreateBoxDto
import com.packy.data.model.createbox.box.CreateBoxRequest
import com.packy.data.model.getbox.GiftBox
import com.packy.data.model.getbox.GiftBoxDto
import com.packy.lib.utils.Resource
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
    suspend fun getBoxDesign(): Resource<List<BoxDesignDto>> =
        httpClient.get("api/v1/admin/design/boxes")
            .toResource()

    suspend fun createBox(
        createBoxRequest: CreateBoxRequest
    ): Resource<CreateBoxDto> = httpClient.post("/api/v1/giftbox") {
        contentType(ContentType.Application.Json)
        setBody(createBoxRequest)
    }.toResource()

    suspend fun getGifBox(
        giftBoxId: String
    ): Resource<GiftBoxDto> =
        httpClient.get("/api/v1/giftboxes/${giftBoxId}")
            .toResource()
}