package com.kairias97.ezitineraries.utils

import android.content.Context
import android.graphics.Color
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Color.parseColor
import android.graphics.Color.colorToHSV
import com.google.android.gms.maps.model.BitmapDescriptor

class ColorUtil {
    companion object {
        fun getMarkerIcon(color: String): BitmapDescriptor {
            val hsv = FloatArray(3)
            Color.colorToHSV(Color.parseColor(color), hsv)
            return BitmapDescriptorFactory.defaultMarker(hsv[0])
        }
        //private val androidColorMaxValue : Int = 16777216
        /*
        fun getAndroidColor(hexColor: String) : Int {
            val decimalColor = Integer.parseInt(hexColor, 16)
            return (decimalColor - androidColorMaxValue)
        }*/
    }
}