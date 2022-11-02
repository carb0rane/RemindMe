package com.kode.remindme.map

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng

object Data {
    var markers: MutableList<LatLng> = arrayListOf()
    const val ACTION_START_SERVICE = "ACTION START SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP SERVICE"

    const val PERM_NOTIFICATION_CHANNEL_ID = "1"
    const val PERM_NOTIFICATION_CHANNEL_NAME = "Permanent Notification"
    const val REM_NOTIFICATION_CHANNEL_ID = "2"
    const val REM_NOTIFICATION_CHANNEL_NAME = "Reminder Notifications"

    const val PENDING_INTENT_REQUEST_CODE = 54

    const val NOTIFICATION_ID = 184
    const val REM_NOTIFICATION_ID = 1704

    const val LOCATION_UPDATE_INTERVAL = 4000L
    const val LOCATION_FASTEST_UPDATE_INTERVAL = 2000L

    const val GEOFENCE_RADIUS = 20.0

    var markerID = "1337"

    fun getId():Int{
        return System.currentTimeMillis().toInt()
    }

    var liveGeofenceLocation = MutableLiveData<LatLng>()
}