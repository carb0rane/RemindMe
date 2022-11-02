package com.kode.remindme.map

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.GeofencingEvent
import com.google.android.gms.maps.model.LatLng
import com.kode.remindme.map.Data.liveGeofenceLocation

private const val TAG = "GeofenceBroadcastReceiver"
class GeofenceBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"you got in geofence !!",Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onReceive: Got inside the Geofence !@ ")

        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)





        val location = geofencingEvent?.triggeringGeofences
        location?.get(0)?.latitude

         val latLng = LatLng(location?.get(0)!!.latitude , location?.get(0)!!.longitude)
        liveGeofenceLocation.value = latLng


    }
}
