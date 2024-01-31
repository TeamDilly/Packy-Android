package com.packy.data.usecase.photo

import com.packy.domain.repository.photo.PhotoRepository
import com.packy.domain.usecase.photo.UploadImageUseCase
import com.packy.lib.utils.Resource
import java.io.File
import javax.inject.Inject

class UploadImageUseCaseImp @Inject constructor(
    private val repository: PhotoRepository
) : UploadImageUseCase {
    override suspend fun uploadImage(
        fileName: String,
        uri: String
    ): Resource<String> =
        repository.uploadFile(
            fileName,
            uri
        )
}