package com.packy.data.remote.profile

import com.packy.data.model.profile.UpdateNickNameRequest
import com.packy.data.model.profile.UpdateProfileDto
import com.packy.data.model.profile.UpdateProfileRequest
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProfileUpdateService  @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun updateProfile(
        nickname: String? = null,
        profileImg: Int? = null
    ): Resource<UpdateProfileDto> = safeRequest {
        httpClient.patch("/api/v1/my-page/profile"){
            contentType(ContentType.Application.Json)
            if(nickname != null){
                setBody(UpdateNickNameRequest(nickname))
            }else if(profileImg != null){
                setBody(UpdateProfileRequest(profileImg))
            }
        }
    }
}