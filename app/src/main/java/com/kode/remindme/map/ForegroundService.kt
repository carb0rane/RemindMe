package com.kode.remindme.map

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.kode.remindme.Notification.DaggerNotificationComponent
import com.kode.remindme.Notification.NotificationModule
import com.kode.remindme.Room.AppModule
import com.kode.remindme.Room.NotesRepo
import com.kode.remindme.Room.RoomModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

private const val TAG = "ForegroundService"

class ForegroundService : LifecycleService() {
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    @Named("cancellable")lateinit var reminder_notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var reminder_notificationManager: NotificationManager

    @Inject
    lateinit var notesrepo: NotesRepo

    var liveLocation = MutableLiveData<LatLng>()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult ?: return
            for (location in locationResult.locations) {
                liveLocation.value = LatLng(location.latitude, location.longitude)
            }


        }
    }

    override fun onCreate() {
        super.onCreate()
        DaggerNotificationComponent.builder().appModule(AppModule(application)).roomModule(
            RoomModule(application)
        ).notificationModule(
            NotificationModule(application)
        ).build()
            .injectservice(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        Data.liveGeofenceLocation.observe(this){
            sendReminderNotification()
        }

    }

    private fun sendReminderNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createReminderNotificationChannel()
        }

       // startForeground(Data.REM_NOTIFICATION_ID, reminder_notificationBuilder.build())


            Data.liveGeofenceLocation.observe(this@ForegroundService) {
                CoroutineScope(Dispatchers.IO).launch {
                    delay(10000)
                    try {
                        val details = notesrepo.getNoti(it.toString())
                        Log.d(TAG, "sendReminderNotification: $it")
                        Log.d(TAG, "sendReminderNotification: $details")
                        reminder_notificationBuilder.setOngoing(false)
                        reminder_notificationBuilder.setContentText(
                            details.mainContent + " "
                        )
                        reminder_notificationBuilder.setContentTitle(
                            details.heading
                        )
                        reminder_notificationManager.notify(
                            Data.REM_NOTIFICATION_ID,
                            reminder_notificationBuilder.build()
                        )
                    } catch (e: Exception) {
                        Log.d(TAG, "sendReminderNotification: $e")
                    }
                }
            }

    }

    private fun createReminderNotificationChannel() {
        val channel = NotificationChannel(
            Data.REM_NOTIFICATION_CHANNEL_ID,
            Data.REM_NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_HIGH
        )
val audioAttributes = AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_NOTIFICATION).build()

         channel.setSound(Uri.parse("android.resource://"+applicationContext.packageName + "/"+ com.kode.remindme.R.raw.notification),audioAttributes)
        channel.enableVibration(true)

        channel.lockscreenVisibility =Notification.VISIBILITY_PUBLIC
        reminder_notificationManager.createNotificationChannel(channel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                Data.ACTION_START_SERVICE -> {
                    startNotificationService()
                    getlocationUpdates()
                }
                Data.ACTION_STOP_SERVICE -> {
                }

                else -> {}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }



    private fun startNotificationService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        startForeground(Data.NOTIFICATION_ID, notificationBuilder.build())
//        CoroutineScope(Dispatchers.Main).launch {
//            liveLocation.observe(this@ForegroundService) {
                notificationBuilder.setContentText(
                    "Live location is being tracked to remind of saved notes"
                )
        notificationBuilder.setContentTitle("Background Tracking is On")
                notificationManager.notify(Data.NOTIFICATION_ID, notificationBuilder.build())
//            }
//        }

    }

    @SuppressLint("MissingPermission")
    fun getlocationUpdates() {

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            Data.LOCATION_FASTEST_UPDATE_INTERVAL
        ).build()

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            Data.PERM_NOTIFICATION_CHANNEL_ID,
            Data.PERM_NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_DEFAULT
        )
            channel.lockscreenVisibility =Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(channel)
    }

}