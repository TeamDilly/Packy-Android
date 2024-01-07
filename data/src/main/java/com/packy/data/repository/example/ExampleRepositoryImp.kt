package com.packy.data.repository.example

import com.packy.data.remote.example.ExampleService
import com.packy.domain.model.example.ExampleDataDto
import com.packy.data.model.example.toDto
import com.packy.domain.repository.ExampleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ExampleRepositoryImp @Inject constructor(
    private val api: ExampleService
) : ExampleRepository {
    override suspend fun getExampleData(): Flow<ExampleDataDto> {
        val exampleResponse = api.getExample()
        val dto = exampleResponse.toDto()

        return flowOf(dto)
    }
}