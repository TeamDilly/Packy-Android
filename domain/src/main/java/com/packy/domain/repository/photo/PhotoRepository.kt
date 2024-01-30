package com.packy.domain.repository.photo

import java.io.File

interface PhotoRepository {
    suspend fun uploadFile(fileName: String, uri: String): String
}