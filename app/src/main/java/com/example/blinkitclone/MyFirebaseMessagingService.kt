package com.example.blinkitclone

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val CHANNEL_ID = "order_updates"
    private val NOTIFICATION_ID = 2 // Use a different ID from your local one

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // This function is called when a push notification is received
        // while the app is in the foreground.
        Log.d("FCM", "From: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")
            sendNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        // This is called when a new FCM registration token is generated.
        // In a real app, you would send this token to your server.
        Log.d("FCM", "Refreshed token: $token")
    }

    private fun sendNotification(title: String?, message: String?) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_vpr) // Your single-color icon
            .setContentTitle(title ?: "New Message")
            .setContentText(message ?: "You have a new notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
