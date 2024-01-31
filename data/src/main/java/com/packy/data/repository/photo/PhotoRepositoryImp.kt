package com.packy.data.repository.photo

import com.packy.data.remote.photo.PhotoService
import com.packy.domain.repository.photo.PhotoRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import java.io.File
import javax.inject.Inject

@Suppress("UNREACHABLE_CODE")
class PhotoRepositoryImp @Inject constructor(
    private val photoService: PhotoService
) : PhotoRepository {
    override suspend fun uploadFile(
        fileName: String,
        uri: String
    ): Resource<String> {
        val uploadPhotoUrlResource = photoService.getUploadUrl(fileName)
        return if (uploadPhotoUrlResource is Resource.Success) {
            val uploadPhotoUrl = uploadPhotoUrlResource.data.url
            photoService.uploadPhoto(
                fileName,
                uploadPhotoUrl,
                uri
            )
        } else {
            uploadPhotoUrlResource.map { "" }
        }
    }

}