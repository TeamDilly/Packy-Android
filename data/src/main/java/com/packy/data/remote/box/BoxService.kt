package com.packy.data.remote.box

import com.packy.data.model.createbox.BoxDesignDto
import com.packy.data.model.createbox.CreateBoxDto
import com.packy.data.model.createbox.KakaoMessageImgUrlDto
import com.packy.data.model.createbox.UpdateBoxDeliverStatusRequest
import com.packy.data.model.createbox.box.CreateBoxRequest
import com.packy.data.model.getbox.GiftBoxDto
import com.packy.domain.model.box.BoxDeliverStatus
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
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

    suspend fun deleteBox(
        giftBoxId: String
    ): Resource<String> = safeRequest {
        httpClient.delete("/api/v1/giftboxes/${giftBoxId}")
    }

    suspend fun updateBoxDeliverStatus(
        giftBoxId: Long,
        boxDeliverStatus: BoxDeliverStatus
    ): Resource<String> = safeRequest {
        httpClient.patch("/api/v1/giftboxes/${giftBoxId}"){
            setBody(UpdateBoxDeliverStatusRequest(boxDeliverStatus))
        }
    }

    suspend fun getKakaoMessageImage(
        giftBoxId: Long
    ): Resource<KakaoMessageImgUrlDto> = safeRequest {
        httpClient.get("/api/v1/giftboxes/${giftBoxId}/kakao-image")
    }
}