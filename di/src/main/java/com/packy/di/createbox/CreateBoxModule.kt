package com.packy.di.createbox

import com.packy.data.repository.createbox.CreateBoxRepositoryImp
import com.packy.data.usecase.createbox.CreateBoxFlagUseCaseImp
import com.packy.data.usecase.createbox.CreateBoxUseCaseImp
import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.usecase.createbox.CreateBoxFlagUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CreateBoxRepositoryModule {

    @Binds
    abstract fun bindCreateBoxRepository(repository: CreateBoxRepositoryImp): CreateBoxRepository

    @Binds
    abstract fun bindCreateBoxFlagUseCase(useCase: CreateBoxFlagUseCaseImp): CreateBoxFlagUseCase

    @Binds
    abstract fun bindCreateBoxUseCase(useCase: CreateBoxUseCaseImp): CreateBoxUseCase
}