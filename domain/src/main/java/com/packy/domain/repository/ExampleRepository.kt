package com.packy.domain.repository

import com.packy.domain.model.example.ExampleDataDto
import kotlinx.coroutines.flow.Flow

interface ExampleRepository {
    suspend fun getExampleData(): Flow<ExampleDataDto>
}