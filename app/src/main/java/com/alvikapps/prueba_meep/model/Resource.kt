package com.alvikapps.prueba_meep.model

import com.google.android.gms.maps.model.LatLng

data class Resource(
    val x: Double,
    val y: Double,
    val companyZoneId: Int,
    val batteryLevel: Int?,
    val bikesAvailable: Int?,
    val name: String?
)
