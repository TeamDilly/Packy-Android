package com.packy.di.reset

import com.packy.data.usecase.reset.ResetCreateBoxUseCaseImp
import com.packy.domain.usecase.reset.ResetCreateBoxUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResetModule {
    @Binds
    abstract fun bindingResetCreateBox(useCase: ResetCreateBoxUseCaseImp): ResetCreateBoxUseCase
}