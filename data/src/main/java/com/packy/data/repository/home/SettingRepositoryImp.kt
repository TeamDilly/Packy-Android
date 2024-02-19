package com.packy.data.repository.home

import com.packy.account.AccountManagerHelper
import com.packy.data.remote.home.SettingService
import com.packy.data.remote.my.MyProfileService
import com.packy.domain.model.my.MyProfile
import com.packy.domain.model.settings.SettingItem
import com.packy.domain.repository.home.SettingRepository
import com.packy.lib.utils.Resource
import com.packy.lib.utils.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingRepositoryImp @Inject constructor(
    private val api: SettingService,
    private val myProfileApi: MyProfileService,
    private val accountManagerHelper: AccountManagerHelper
) : SettingRepository {
    override suspend fun getSettings(): Flow<Resource<List<SettingItem>>> = flow {
        emit(Resource.Loading())
        val settingResource = api.getSettings()
        emit(
            settingResource.map { list ->
                list.map { setting -> setting.toEntity() }
            }
        )
    }

    override suspend fun getMyProfile(): Flow<Resource<MyProfile>> = flow {
        emit(Resource.Loading())
        val myProfile = myProfileApi.getMyProfile()
        if(myProfile is Resource.Success){
            accountManagerHelper.setNickName(myProfile.data.nickname)
        }
        emit(
            myProfile.map { dto -> dto.toEntity() }
        )
    }
}
