package com.alvikapps.prueba_meep.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alvikapps.prueba_meep.R
import com.alvikapps.prueba_meep.repository.Repository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map:GoogleMap
    private lateinit var viewModel: MainViewModel
    private lateinit var markersEco : MutableList<LatLng>
    private lateinit var markersBike : MutableList<LatLng>

    // hardcoded for Lisbon

    val lowerLeftLatLon= LatLng(38.711046,-9.160096)
    val lowerLat = lowerLeftLatLon.latitude.toString() + "," + lowerLeftLatLon.longitude.toString()

    val upperRightLatLon= LatLng(38.739429,-9.137115)
    val upperLat = upperRightLatLon.latitude.toString() + "," + upperRightLatLon.longitude.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createFragmentMap()

        val repository =  Repository()
        val viewModelFactory =  MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getResources(lowerLat, upperLat)
        Log.d("Respuesta", upperLat)

        viewModel.myResponse.observe(this, Observer{response ->
            if(response.isSuccessful){
                response.body()?.forEach{
                    // aquí marcadores
                    //473 eco, 412 bici
                    when (it.companyZoneId){
                        473 -> markersEco.add(LatLng(it.x,it.y))
                        412 -> markersBike.add(LatLng(it.x,it.y))
                    }

                    Log.d("Respuesta", it.x.toString())
                    Log.d("Respuesta", it.y.toString())
                    Log.d("Respuesta", it.companyZoneId.toString())
                }
            }
            else
                Log.d("Respuesta: ", "Error " + response.code().toString())
        })
    }

    private fun createFragmentMap() {
        var mapFragment:SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        addMarkers()
    }

    private fun addMarkers() {
        // hardcoded lisbon for now
        val coordinates = LatLng(38.72283601589804, -9.125310404859851)

        markersBike.forEach { place ->
            val marker = map.addMarker(
                MarkerOptions()
                    .title("Bici")
                    .position(LatLng(place.latitude, place.longitude))
                    //.icon(R.drawable.ic_bike_24)
            )
        }
        val marker = MarkerOptions().position(coordinates).title("This is Lisbon")
        map.addMarker(marker)

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 17f),4000, null
        )
    }
}