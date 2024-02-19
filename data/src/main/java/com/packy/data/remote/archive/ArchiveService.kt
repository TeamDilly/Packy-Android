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
import io.ktor.client.request.parameter
import javax.inject.Inject

class ArchiveService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getArchivePhotos(lastId: Int?): Resource<PaginationDto<ArchivePhotoDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/photos"){
            if(lastId != null){
                parameter(
                    "last-photo-id",
                    "$lastId"
                )
            }
        }
    }

    suspend fun getArchiveMusics(lastId: Int?): Resource<PaginationDto<ArchiveMusicDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/musics"){
            if(lastId != null){
                parameter(
                    "last-giftbox-id",
                    "$lastId"
                )
            }
        }
    }

    suspend fun getArchiveLetters(lastId: Int?): Resource<PaginationDto<ArchiveLetterDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/letters"){
            if(lastId != null){
                parameter(
                    "last-letter-id",
                    "$lastId"
                )
            }
        }
    }

    suspend fun getArchiveGifts(lastId: Int?): Resource<PaginationDto<ArchiveGiftDto>> = safeRequest {
        httpClient.get("/api/v1/gifts/items"){
            if(lastId != null){
                parameter(
                    "last-giftbox-id",
                    "$lastId"
                )
            }
        }
    }
}