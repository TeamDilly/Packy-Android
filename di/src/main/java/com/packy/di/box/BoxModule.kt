package com.packy.di.box

import com.packy.data.remote.box.BoxService
import com.packy.data.repository.box.BoxRepositoryImp
import com.packy.data.usecase.box.DeleteBoxUseCaseImp
import com.packy.data.usecase.box.GetBoxDesignUseCaseImp
import com.packy.data.usecase.box.GetBoxUseCaseImp
import com.packy.data.usecase.box.UpdateBoxDeliverStatusUseCaseImp
import com.packy.di.network.Packy
import com.packy.domain.repository.box.BoxRepository
import com.packy.domain.usecase.box.DeleteBoxUseCase
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.box.GetBoxUseCase
import com.packy.domain.usecase.box.UpdateBoxDeliverStatusUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BoxServiceModule {
    @Provides
    @Singleton
    fun provideBoxService(
        @Packy httpClient: HttpClient
    ): BoxService =
        BoxService(httpClient)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BoxRepositoryModule {

    @Binds
    abstract fun bindBoxRepository(repository: BoxRepositoryImp): BoxRepository

    @Binds
    abstract fun bindBoxUseCase(useCase: GetBoxDesignUseCaseImp): GetBoxDesignUseCase

    @Binds
    abstract fun bindGetBoxUseCase(ussCase: GetBoxUseCaseImp): GetBoxUseCase

    @Binds
    abstract fun bindDeleteBoxUseCase(useCase: DeleteBoxUseCaseImp): DeleteBoxUseCase

    @Binds
    abstract fun bindUpdateBoxDeliverStatusUseCase(useCase: UpdateBoxDeliverStatusUseCaseImp): UpdateBoxDeliverStatusUseCase
}