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
        val marcadorMikedi = LatLng(43.172993, -2.633388)
        mMap.addMarker(MarkerOptions().position(marcadorMikedi).title("Cerdo de Mikeldi"))

        //Feria de Durango
        val marcadorFeria = LatLng(43.171167, -2.630722)
        mMap.addMarker(MarkerOptions().position(marcadorFeria).title("Feria de Durango"))

        //Sirena de Bombardeo
        val marcadorSirena = LatLng(43.168194, -2.628278)
        mMap.addMarker(MarkerOptions().position(marcadorSirena).title("Sirena de Bombardeo"))

        //Basilica de Santa Ana
        val marcadorBasilica = LatLng(43.168389, -2.631222)
        mMap.addMarker(MarkerOptions().position(marcadorBasilica).title("Basilica de Santa Ana"))

        //Personaje Durango Patxikotxu
        val marcadorPersonaje = LatLng(43.166778, -2.631833)
        mMap.addMarker(MarkerOptions().position(marcadorPersonaje).title("Personaje Durango"))

        //Dulce Artopila
        val marcadorArtopila = LatLng(43.166778, -2.631833)
        mMap.addMarker(MarkerOptions().position(marcadorArtopila).title("Dulce Artopila"))

        //Escudo Durango
        val marcadorEscudo = LatLng(43.165611, -2.632333)
        mMap.addMarker(MarkerOptions().position(marcadorEscudo).title("Escudo Durango"))

        // Listener para clics en los marcadores
        mMap.setOnMarkerClickListener { marker ->
            when (marker) {
                marcadorMikedi -> abrirActividad(CerdoMikeldiActivity::class.java)
                marcadorFeria -> abrirActividad(FeriaDurangoActivity::class.java)
                marcadorSirena -> abrirActividad(SirenaBombardeoActivity::class.java)
                marcadorBasilica -> abrirActividad(BasilicaSantaAnaActivity::class.java)
                marcadorPersonaje -> abrirActividad(PersonajeDurangoActivity::class.java)
                marcadorArtopila -> abrirActividad(DulceArtopilaActivity::class.java)
                marcadorEscudo -> abrirActividad(EscudoDurangoActivity::class.java)
            }
            true
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcadorBasilica, 16f))
    }

    // Función genérica para abrir actividades
    private fun <T> abrirActividad(clase: Class<T>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}