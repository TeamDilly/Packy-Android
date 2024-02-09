package com.packy.data.usecase.my

import com.packy.domain.repository.my.MyInfoRepository
import com.packy.domain.usecase.my.GetMyNickNameUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyNickNameUseCaseImp @Inject constructor(
    private val repository: MyInfoRepository
) : GetMyNickNameUseCase {
    override suspend fun getNickName(): Flow<String?> = repository.getMyNickName()
}