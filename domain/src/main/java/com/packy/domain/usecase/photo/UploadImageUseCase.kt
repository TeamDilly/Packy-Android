package com.packy.domain.usecase.photo

import com.packy.lib.utils.Resource

interface UploadImageUseCase {
    suspend fun uploadImage(fileName: String, uri: String): Resource<String>
}