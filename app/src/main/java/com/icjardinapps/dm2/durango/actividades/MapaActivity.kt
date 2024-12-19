package com.icjardinapps.dm2.durango.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.icjardinapps.dm2.durango.R
import com.icjardinapps.dm2.durango.databinding.ActivityMapaBinding

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Añadir marcadores al mapa
        //Cerdo de Mikeldi
        val mikedi = LatLng(43.172993, -2.633388)
        mMap.addMarker(MarkerOptions().position(mikedi).title("Cerdo de Mikeldi"))

        //Feria de Durango
        val feria = LatLng(43.171167, -2.630722)
        mMap.addMarker(MarkerOptions().position(feria).title("Feria de Durango"))

        //Sirena de Bombardeo
        val sirena = LatLng(43.168194, -2.628278)
        mMap.addMarker(MarkerOptions().position(sirena).title("Sirena de Bombardeo"))

        //Basilica de Santa Ana
        val basilica = LatLng(43.168389, -2.631222)
        mMap.addMarker(MarkerOptions().position(basilica).title("Basilica de Santa Ana"))

        //Personaje Durango Patxikotxu
        val personajeDurango = LatLng(43.166778, -2.631833)
        mMap.addMarker(MarkerOptions().position(personajeDurango).title("Personaje Durango"))

        //Dulce Artopila
        val artopila = LatLng(43.166778, -2.631833)
        mMap.addMarker(MarkerOptions().position(artopila).title("Dulce Artopila"))

        //Escudo Durango
        val escudo = LatLng(43.165611, -2.632333)
        mMap.addMarker(MarkerOptions().position(escudo).title("Escudo Durango"))

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(mikedi))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(basilica, 16f))
    }
}