package com.packy.data.repository.my

import com.packy.account.AccountManagerHelper
import com.packy.domain.repository.my.MyInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MyInfoRepositoryImp @Inject constructor(
    private val accountManagerHelper: AccountManagerHelper
): MyInfoRepository{
    override suspend fun getMyNickName(): Flow<String?> = flowOf(accountManagerHelper.getNickName())

}