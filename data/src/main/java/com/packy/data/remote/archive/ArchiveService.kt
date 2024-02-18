package com.packy.data.remote.archive

import androidx.paging.PagingData
import com.packy.data.model.archive.ArchiveGiftDto
import com.packy.data.model.archive.ArchiveLetterDto
import com.packy.data.model.archive.ArchiveMusicDto
import com.packy.data.model.archive.ArchivePhotoDto
import com.packy.data.model.common.PaginationDto
import com.packy.domain.model.archive.ArchiveGift
import com.packy.lib.utils.Resource
import com.packy.lib.utils.safeRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ArchiveService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getArchivePhotos(): Resource<PaginationDto<ArchivePhotoDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/photos")
    }

    suspend fun getArchiveMusics(): Resource<PaginationDto<ArchiveMusicDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/musics")
    }

    suspend fun getArchiveLetters(): Resource<PaginationDto<ArchiveLetterDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/letters")
    }

    suspend fun getArchiveGifts(): Resource<PaginationDto<ArchiveGiftDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/items")
    }
}