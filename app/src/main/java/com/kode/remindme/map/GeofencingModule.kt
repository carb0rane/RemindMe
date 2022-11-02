package com.kode.remindme.map

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng





private const val TAG = "GeofencingModule"

object GeofencingModule  {
     var pendingIntent: PendingIntent?=null



    fun getBroadcastPendingIntent(context: Context): PendingIntent {
        if (pendingIntent != null) {
            return pendingIntent as PendingIntent
        }
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        pendingIntent =
            PendingIntent.getBroadcast(context, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return pendingIntent as PendingIntent!!
    }

    fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }
    fun getGeofence(ID: String, latLng: LatLng, radius: Float, transitionTypes: Int): Geofence {
        return Geofence.Builder()
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setRequestId(ID)
            .setTransitionTypes(transitionTypes)
            .setLoiteringDelay(5000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }
}