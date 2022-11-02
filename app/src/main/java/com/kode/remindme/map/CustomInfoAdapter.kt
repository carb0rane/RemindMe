package com.kode.remindme.map

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.kode.remindme.R

class CustomInfoAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    val contentView = (context as Activity).layoutInflater.inflate(R.layout.mapmarkerinfo, null)

    override fun getInfoContents(p0: Marker): View? {
      renderView(p0,contentView)
        return contentView
    }

    override fun getInfoWindow(p0: Marker): View? {
        renderView(p0,contentView)
        return contentView
    }

    private fun renderView(marker: Marker?, view: View) {
        val heading = marker?.title
        val description = marker?.snippet

        val headingView = view.findViewById<TextView>(R.id.tvMarkerInfoHeading)
        headingView.text = heading

        val descriptionView = view.findViewById<TextView>(R.id.tvMarkerInfoDescription)
        descriptionView.text = description
    }

}