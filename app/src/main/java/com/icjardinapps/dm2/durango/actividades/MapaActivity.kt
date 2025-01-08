package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.icjardinapps.dm2.durango.MainActivity
import com.icjardinapps.dm2.durango.R
import com.icjardinapps.dm2.durango.databinding.ActivityMapaBinding

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo para ser usado.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Listener para el botón Volver
        binding.btnVolver.setOnClickListener {
            abrirActividad(MainActivity::class.java)
        }

        // Listener para el botón Puzzle Final
        binding.btnPuzzleFinal.setOnClickListener {
            abrirActividad(PuzleActivity::class.java)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Añadir marcadores al mapa
        val marcadorMikedi = mMap.addMarker(MarkerOptions().position(LatLng(43.172993, -2.633388)))
        marcadorMikedi?.tag = "mikedi"

        val marcadorFeria = mMap.addMarker(MarkerOptions().position(LatLng(43.171167, -2.630722)))
        marcadorFeria?.tag = "feria"

        val marcadorSirena = mMap.addMarker(MarkerOptions().position(LatLng(43.168194, -2.628278)))
        marcadorSirena?.tag = "sirena"

        val marcadorBasilica = mMap.addMarker(MarkerOptions().position(LatLng(43.168389, -2.631222)))
        marcadorBasilica?.tag = "basilica"

        val marcadorPersonajeArtopila = mMap.addMarker(MarkerOptions().position(LatLng(43.166778, -2.631833)))
        marcadorPersonajeArtopila?.tag = "personajeArtopila"

        val marcadorEscudo = mMap.addMarker(MarkerOptions().position(LatLng(43.165611, -2.632333)))
        marcadorEscudo?.tag = "escudo"

        // Listener para clics en los marcadores
        mMap.setOnMarkerClickListener { marker ->
            when (marker.tag) {
                "mikedi" -> abrirActividad(MikeldiActivity::class.java)
                "feria" -> abrirActividad(FeriaActivity::class.java)
                "sirena" -> abrirActividad(SirenaActivity::class.java)
                "basilica" -> abrirActividad(BasilicaActivity::class.java)
                "personajeArtopila" -> abrirActividad(PatxikotxuActivity::class.java)
                "artopila" -> abrirActividad(ArtopilActivity::class.java)
                "escudo" -> abrirActividad(EscudoActivity::class.java)
                else -> false  // Si no se encuentra el tag, no hace nada
            }
            true
        }

        // Mover la cámara al centro del mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcadorBasilica!!.position, 16f))
    }

    // Función para abrir actividades
    private fun <T> abrirActividad(clase: Class<T>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}