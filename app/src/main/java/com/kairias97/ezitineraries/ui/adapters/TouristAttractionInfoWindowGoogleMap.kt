package com.kairias97.ezitineraries.ui.adapters

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.kairias97.ezitineraries.R
import com.kairias97.ezitineraries.model.TouristAttraction
import com.kairias97.ezitineraries.model.TouristVisit

class TouristAttractionInfoWindowGoogleMap : GoogleMap.InfoWindowAdapter {

    private lateinit var mContext : Context
    constructor(context: Context) {
        mContext = context
    }

    override fun getInfoContents(marker: Marker?): View? {
        val infoView = (this.mContext as AppCompatActivity)
                .layoutInflater
                .inflate(R.layout.map_tourist_attraction_info_window,
                null)
        val poi = marker?.tag
        val nameTextView = infoView.findViewById<TextView>(R.id.textView_name)
        val addressTextView = infoView.findViewById<TextView>(R.id.textView_address)
        val categoryTextView = infoView.findViewById<TextView>(R.id.textView_category)
        val attractionRatingBar = infoView.findViewById<RatingBar>(R.id.textView_rating)
        if (poi is TouristAttraction) {
            nameTextView.text = poi.name
            addressTextView.text = poi.address
            categoryTextView.text = poi.category?.name
            attractionRatingBar.rating = poi.rating?.toFloat() ?: 0.toFloat()
        } else if (poi is TouristVisit) {
            nameTextView.text = "(${poi.visitOrder}) ${poi.touristAttraction?.name}"
            addressTextView.text = poi.touristAttraction?.address
            categoryTextView.text = poi.touristAttraction?.category?.name
            attractionRatingBar.rating = poi.touristAttraction?.rating?.toFloat() ?: 0.toFloat()
        }

        return infoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}