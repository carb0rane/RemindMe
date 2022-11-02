package com.kode.remindme.map

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.LocationProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kode.remindme.R
import com.kode.remindme.databinding.FragmentMapsBinding
import com.kode.remindme.map.Data.markers

private const val TAG = "MapsFragment"
class MapsFragment : Fragment(), OnMapReadyCallback, OnMarkerClickListener {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var map: GoogleMap
    private var marker: Marker? = null
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var latLng: LatLng
    private var ID = "1337"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ID = Data.getId().toString()
        Data.markerID = ID
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        binding.btnAddMarker.setOnClickListener {

            if (marker != null) {
                addGeofence(latLng,Data.GEOFENCE_RADIUS.toFloat())
                markers.clear()
                markers.add(marker!!.position)
                getActivity()?.supportFragmentManager?.popBackStack()
            }

        }
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isMyLocationButtonEnabled = true
        }
        map.isMyLocationEnabled = true
        onMapLongClick()


    }

    fun onMapLongClick() {
        map.setOnMapLongClickListener {
                map.clear()
            marker = map.addMarker(MarkerOptions().position(it))
            addCircle(it, Data.GEOFENCE_RADIUS, map)
           latLng = it

        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        p0.remove()
        return true
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(latLng: LatLng, rad: Float) {
        val geofence = GeofencingModule.getGeofence(
            ID, latLng, rad,
            Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT
        )

        val geofenceRequest = GeofencingModule.getGeofencingRequest(geofence)
        val pendingIntent = GeofencingModule.getBroadcastPendingIntent(requireContext())

        geofencingClient.addGeofences(geofenceRequest,pendingIntent)
            .addOnSuccessListener {
                Log.d(TAG, "addGeofence: Successfully added geofence")
            }
            .addOnFailureListener {
                Log.d(TAG, "addGeofence: Failed to add the geofence $it ")
            }
    }

    private fun addCircle(latLng: LatLng, rad: Double, map: GoogleMap) {
        val circleOptions = CircleOptions().apply {
            center(latLng)
            radius(rad)
            strokeColor(Color.argb(255, 255, 0, 0))
            fillColor(Color.argb(64, 255, 0, 0))
            strokeWidth(4F)
        }

        map.addCircle(circleOptions);
    }
}