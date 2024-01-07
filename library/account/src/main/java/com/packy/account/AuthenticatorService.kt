package com.packy.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticatorService: Service() {
    override fun onBind(p0: Intent?): IBinder {
        val authenticator = Authenticator(this)
        return authenticator.iBinder
    }
}