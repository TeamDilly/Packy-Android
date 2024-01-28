package com.packy.data.remote.createbox

import com.packy.data.model.createbox.StickerDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class StickerService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getSticker(id: Int?): Resource<StickerDto> =
        httpClient.get(urlString = "/api/v1/admin/design/stickers"){
            if(id != null){
                parameter("lastStickerId", "$id")
            }
        }
            .toResource()
}