package com.packy.domain.usecase.photo

interface UploadImageUseCase {
    suspend fun uploadImage(fileName: String, uri: String): String
}