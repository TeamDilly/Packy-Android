package com.packy.domain.usecase.example

import com.packy.domain.model.example.ExampleDataDto
import kotlinx.coroutines.flow.Flow

interface ExampleUseCase {
    suspend fun getExample(): Flow<ExampleDataDto>

    suspend fun getSamplePref(): Flow<String>

    suspend fun setSamplePref(value: String)
}