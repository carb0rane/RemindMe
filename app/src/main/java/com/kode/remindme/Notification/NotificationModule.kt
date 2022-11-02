package com.kode.remindme.Notification

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.kode.remindme.UI.MainActivity
import com.kode.remindme.R
import com.kode.remindme.map.Data
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NotificationModule {
    var context:Context?=null

    constructor(mApplication: Application){
        this.context = mApplication.applicationContext
    }
    @Provides
    fun providePendingIntent(): PendingIntent {
        return PendingIntent.getActivity(
            context,
            Data.PENDING_INTENT_REQUEST_CODE,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT

        )
    }

    @Provides
    fun provideNotificationBuilder(intent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context!!,
            Data.PERM_NOTIFICATION_CHANNEL_ID
        )
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_baseline_gps_fixed_24)
            .setContentText("Keep Studying Buddy")
            .setContentTitle("This is title")
            .setColorized(true)
            .setColor(Color.argb(255,255,0,0))
            .setContentIntent(intent)
    }

    @Provides
    @Named("cancellable")
    fun provideCancellableNotificationBuilder(intent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context!!,
            Data.REM_NOTIFICATION_CHANNEL_ID
        )
            .setAutoCancel(false)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_baseline_gps_fixed_24)
            .setContentText("Keep Studying Buddy")
            .setContentTitle("This is title")
            .setColorized(true)
            .setColor(Color.argb(255,0,0,255))
            .setContentIntent(intent)
    }

    @Provides
    fun provideNotificationManger(): NotificationManager {
        return context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}