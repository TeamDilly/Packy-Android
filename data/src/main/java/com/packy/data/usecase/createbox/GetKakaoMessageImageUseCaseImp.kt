package com.packy.data.usecase.createbox

import com.packy.domain.repository.createbox.CreateBoxRepository
import com.packy.domain.repository.home.HomeRepository
import com.packy.domain.usecase.createbox.GetKakaoMessageImageUseCase
import com.packy.lib.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKakaoMessageImageUseCaseImp @Inject constructor(
    private val repository: CreateBoxRepository
) : GetKakaoMessageImageUseCase {
    override suspend fun getKakaoMessageImage(giftBoxId: Long): Flow<Resource<String>> =
        repository.getKakaoMessageImage(giftBoxId)
}