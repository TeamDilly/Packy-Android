package com.packy.root

import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.packy.account.AccountManagerHelper
import com.packy.core.analytics.FirebaseAnalyticsWrapper
import com.packy.di.BuildConfig
import com.packy.domain.model.usable.UsableStatus
import com.packy.domain.usecase.auth.LogoutUseCase
import com.packy.domain.usecase.usable.GetUsableUseCase
import com.packy.lib.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootComposeViewModel @Inject constructor(
    private val accountHelper: AccountManagerHelper,
    private val logoutUseCase: LogoutUseCase,
    private val getUsableUseCase: GetUsableUseCase
) : ViewModel() {

    suspend fun checkUserStatusOnAppEntry(): Flow<UserState> =
        getUsableUseCase.getUsable(BuildConfig.VERSION_NAME)
            .map { usable ->
                when (usable) {
                    is Resource.Loading -> UserState.LOADING
                    is Resource.ApiError,
                    is Resource.NetworkError,
                    is Resource.NullResult -> UserState.NOT_REGISTERED

                    is Resource.Success -> {
                        FirebaseAnalyticsWrapper.setUserId(usable.data.memberId.toString())
                        if (usable.data.isAvailable) {
                            val account = accountHelper.getAccount()
                            if (account != null) {
                                UserState.REGISTERED
                            } else {
                                UserState.NOT_REGISTERED
                            }
                        } else {
                            when (usable.data.reason) {
                                UsableStatus.INVALID_STATUS -> UserState.NOT_REGISTERED
                                UsableStatus.NEED_UPDATE -> UserState.NEED_UPDATE
                                null -> UserState.NOT_REGISTERED
                            }
                        }
                    }
                }
            }

    suspend fun logout() {
        accountHelper.removeAuthToken()
        logoutUseCase.logout()
    }
}