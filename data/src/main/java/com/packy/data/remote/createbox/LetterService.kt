package com.packy.data.remote.createbox

import com.packy.data.model.createbox.LetterEnvelopeDto
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import com.packy.lib.utils.toResource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class LetterService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getLetterEnvelope(): Resource<List<LetterEnvelopeDto>> = safeRequest {
        httpClient.get(urlString = "/api/v1/admin/design/envelopes")
    }
}