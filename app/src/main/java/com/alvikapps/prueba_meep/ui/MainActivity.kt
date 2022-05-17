package com.alvikapps.prueba_meep.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alvikapps.prueba_meep.R
import com.alvikapps.prueba_meep.model.Resource
import com.alvikapps.prueba_meep.repository.Repository
import com.alvikapps.prueba_meep.utils.BitmapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewModel: MainViewModel
    private lateinit var markers: MutableList<Resource>

    // hardcoded for Lisbon
    val lowerLeftLatLon = LatLng(38.711046, -9.160096)
    val lowerLat = lowerLeftLatLon.latitude.toString() + "," + lowerLeftLatLon.longitude.toString()
    val upperRightLatLon = LatLng(38.739429, -9.137115)
    val upperLat =
        upperRightLatLon.latitude.toString() + "," + upperRightLatLon.longitude.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createFragmentMap()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getResources(lowerLat, upperLat)
/*
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                markers = response.body()!! as MutableList<Resource>
                Log.d("marker", markers.size.toString())

                response.body()?.forEach {

                    Log.d("Respuesta", it.x.toString())
                    Log.d("Respuesta", it.y.toString())
                    Log.d("Respuesta", it.companyZoneId.toString())
                    Log.d("Respuesta", "battery: " + it.batteryLevel.toString())
                    Log.d("Respuesta", "bikes#: " + it.bikesAvailable.toString())
                    Log.d("Respuesta", "address: " + it.name.toString())
                    Log.d("Respuesta", "SIZE: " + markers.size.toString() + " elementos")
                    Log.d("Respuesta", "SIZE2: " + viewModel.myResponse + " elementos")
                    Log.d("Respuesta", "--------------------------")
                }

            } else
                Log.d("Respuesta: ", "Error " + response.code().toString())
        })

 */
    }

    private fun createFragmentMap() {
        var mapFragment: SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        addMarkers()
    }

    private val bikeIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.teal_700)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_bike_24, color)
    }
    private val scooterIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(this, R.color.orange_200)
        BitmapHelper.vectorToBitmap(this, R.drawable.ic_scooter_24, color)
    }

    private fun addMarkers() {
        // hardcoded lisbon for now
        val coordinates = LatLng( 38.728523, -9.148353)

        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.forEach {
                    if(it.companyZoneId == 473){
                        val marker = map.addMarker(
                            MarkerOptions()
                                .title("Electric Scooter")
                                .position(LatLng(it.y, it.x))
                                .icon(scooterIcon)
                                .snippet(it.batteryLevel.toString() + "%")
                        )
                    }
                    else if(it.companyZoneId == 412){
                        val marker = map.addMarker(
                            MarkerOptions()
                                .title(it.name.toString())
                                .position(LatLng(it.y, it.x))
                                .icon(bikeIcon)
                                .snippet(it.bikesAvailable.toString() + " bikes")
                        )
                    }
                }

            } else
                Log.d("Respuesta: ", "Error " + response.code().toString())
        })

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 14f), 3500, null
        )
    }
}