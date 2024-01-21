package com.packy.data.remote.createbox

import com.packy.data.model.createbox.LatterEnvelopeDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class LatterService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getLatterEnvelope(): Resource<List<LatterEnvelopeDto>> =
        httpClient.get(urlString = "api/v1/admin/design/letters")
            .toResource<List<LatterEnvelopeDto>>()

}