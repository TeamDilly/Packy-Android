package com.packy.data.repository.photo

import com.packy.data.remote.photo.PhotoService
import com.packy.domain.repository.photo.PhotoRepository
import com.packy.lib.utils.Resource
import java.io.File
import javax.inject.Inject

class PhotoRepositoryImp @Inject constructor(
    private val photoService: PhotoService
) : PhotoRepository {
    override suspend fun uploadFile(
        fileName: String,
        uri: String
    ): String {
        val uploadPhotoUrlResource = photoService.getUploadUrl(fileName)
        return if (uploadPhotoUrlResource is Resource.Success) {
            val uploadPhotoUrl = uploadPhotoUrlResource.data.url
            photoService.uploadPhoto(
                uploadPhotoUrl,
                uri
            )
            ""
        } else {
            ""
        }
    }

}