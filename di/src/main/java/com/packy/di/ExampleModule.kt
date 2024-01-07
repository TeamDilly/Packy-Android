package com.packy.di

import com.packy.data.remote.example.ExampleService
import com.packy.data.repository.example.ExampleRepositoryImp
import com.packy.data.usecase.ExampleUseCaseImp
import com.packy.domain.repository.ExampleRepository
import com.packy.domain.usecase.example.ExampleUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ExampleModule {
    @Provides
    fun providerExampleService(retrofit: Retrofit): ExampleService =
        retrofit.create(ExampleService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ExampleRepositoryModule {

    @Binds
    abstract fun bindExampleRepository(exampleRepository: ExampleRepositoryImp): ExampleRepository

    @Binds
    abstract fun bindExampleUserCase(exampleUseCase: ExampleUseCaseImp): ExampleUseCase
}

