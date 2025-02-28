package com.example.notificationapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Singleton


class FirebaseNotification : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        /*if (message.notification != null) {
            showNotification(message.notification!!.title, message.notification!!.body)
        }*/
        Log.d(TAG, "onMessageReceived: FCM message received")
        val title = message.notification?.title
        val desc = message.notification?.body
        message.data.let {
            sendNotification(
                title ?: "Title",
                desc ?: "message",
                it["firstname"] ?: "firstname",
            )
        }
        super.onMessageReceived(message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(title: String?, message: String?) {
        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(1, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(title: String, desc: String, firstname: String) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("firstname", firstname)
            putExtra("title", title)
            putExtra("desc", desc)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, NftValue.valuenft, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(desc)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NftValue.valuenft, notificationBuilder.build())
        ++NftValue.valuenft
    }
}

@Singleton
object NftValue{
    var valuenft = 0
}