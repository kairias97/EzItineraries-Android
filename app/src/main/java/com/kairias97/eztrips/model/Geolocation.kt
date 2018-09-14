package com.kairias97.eztrips.model

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.NotNull

class Geolocation {
    @NotNull
    @Column(name = "latitude")
    var latitude : Double? = null
    @NotNull
    @Column(name = "longitude")
    var longitude : Double? = null

}