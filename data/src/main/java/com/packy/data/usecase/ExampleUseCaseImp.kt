package com.packy.data.usecase

import com.packy.data.local.GlobalPrefManager
import com.packy.domain.model.example.ExampleDataDto
import com.packy.domain.repository.ExampleRepository
import com.packy.domain.usecase.example.ExampleUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExampleUseCaseImp @Inject constructor(
    private val globalPrefManager: GlobalPrefManager,
    private val repository: ExampleRepository
): ExampleUseCase {
    override suspend fun getExample(): Flow<ExampleDataDto> = repository.getExampleData()
    override suspend fun getSamplePref(): Flow<String> = globalPrefManager.globalSample.getData()
    override suspend fun setSamplePref(value: String) = globalPrefManager.globalSample.putData(value)
}