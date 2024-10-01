package com.packy.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager

object NotificationConstants {
    const val PACKY_CHANNEL_ID = "packy_channel"
    const val PACKY_CHANNEL_NAME = "packy"
    val PACKY_CHANNEL = NotificationChannel(
        PACKY_CHANNEL_ID,
        PACKY_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH
    )

}