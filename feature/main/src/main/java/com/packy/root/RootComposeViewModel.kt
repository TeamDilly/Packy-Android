package com.packy.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.packy.account.AccountManagerHelper
import com.packy.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootComposeViewModel @Inject constructor(
    private val accountHelper: AccountManagerHelper,
    private val logoutUseCase: LogoutUseCase
): ViewModel(){

    fun checkUserStatusOnAppEntry(): UserState{
        val account = accountHelper.getAccount()
        return if(account != null){
            UserState.REGISTERED
        }else{
            UserState.NOT_REGISTERED
        }
    }

    fun logout(){
        viewModelScope.launch {
            accountHelper.removeAuthToken()
            logoutUseCase.logout()
        }
    }
}