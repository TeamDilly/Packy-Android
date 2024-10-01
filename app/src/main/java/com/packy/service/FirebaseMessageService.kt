package com.packy.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.packy.R
import com.packy.common.notification.NotificationConstants
import com.packy.common.notification.NotificationConstants.PACKY_CHANNEL

class FirebaseMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("LOGEE token $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("packy")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notification = NotificationCompat.Builder(this, NotificationConstants.PACKY_CHANNEL_ID)
            .setSmallIcon(R.drawable.packy_icon_foreground)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setContentIntent(
                PendingIntent.getActivity(
                    this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).apply {
            notify(0, notification.build())
        }
    }
}