package com.packy.data.remote.box

import com.packy.data.model.BoxDesignDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class BoxService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getBoxDesign(): Resource<List<BoxDesignDto>> =
        httpClient.get("api/v1/admin/design/boxes")
            .toResource()
}