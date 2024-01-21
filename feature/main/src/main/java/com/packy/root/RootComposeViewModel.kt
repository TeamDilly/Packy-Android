package com.packy.root

import androidx.lifecycle.ViewModel
import com.packy.account.AccountManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RootComposeViewModel @Inject constructor(
    private val accountHelper: AccountManagerHelper
): ViewModel(){
    fun checkUserStatusOnAppEntry(): UserState{
        val account = accountHelper.getAccount()
        return if(account != null){
            UserState.REGISTERED
        }else{
            UserState.NOT_REGISTERED
        }
    }
}