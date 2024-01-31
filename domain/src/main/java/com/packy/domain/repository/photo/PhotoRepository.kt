package com.packy.domain.repository.photo

import com.packy.lib.utils.Resource
import java.io.File

interface PhotoRepository {
    suspend fun uploadFile(fileName: String, uri: String): Resource<String>
}