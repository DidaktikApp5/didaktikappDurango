package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.PolylineOptions
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
            abrirActividad(MainActivity::class.java,"main")
        }

        // Listener para el botón Puzzle Final
        binding.btnPuzzleFinal.setOnClickListener {
            abrirActividad(PuzleActivity::class.java,"puzle")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Cargar el vector desde los recursos
        val vectorDrawableMikeldi: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.mikeldimapa, null)
        val vectorDrawableBasilica: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.basilicamapa, null)
        val vectorDrawableFeria: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.feriamapa, null)
        val vectorDrawableSirena: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.sirenamapa, null)
        val vectorDrawableEscudo: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.escudomapa, null)
        val vectorDrawableArtopila: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.artopilamapa, null)

        // Convertir el vector drawable a Bitmap
        val iconBitmapMikeldi = getBitmapFromVectorDrawable(vectorDrawableMikeldi)
        val iconBitmapBasilica = getBitmapFromVectorDrawable(vectorDrawableBasilica)
        val iconBitmapSirena = getBitmapFromVectorDrawable(vectorDrawableSirena)
        val iconBitmapFeria = getBitmapFromVectorDrawable(vectorDrawableFeria)
        val iconBitmapArtopila = getBitmapFromVectorDrawable(vectorDrawableArtopila)
        val iconBitmapEscudo = getBitmapFromVectorDrawable(vectorDrawableEscudo)

        // Añadir marcadores al mapa
        val marcadorMikedi = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.172993, -2.633388))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapMikeldi)))
        marcadorMikedi?.tag = "mikeldi"

        val marcadorFeria = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.171167, -2.630722))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapFeria)))
        marcadorFeria?.tag = "feria"

        val marcadorSirena = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.168194, -2.628278))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapSirena)))
        marcadorSirena?.tag = "sirena"

        val marcadorBasilica = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.168389, -2.631222))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapBasilica)))
        marcadorBasilica?.tag = "basilica"

        val marcadorPersonajeArtopila = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.166778, -2.631833))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapArtopila)))
        marcadorPersonajeArtopila?.tag = "personajeArtopila"

        val marcadorEscudo = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.165611, -2.632333))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapEscudo)))
        marcadorEscudo?.tag = "escudo"

        // Patrón de línea punteada
        val pattern: List<PatternItem> = listOf(
            Dash(30f),  // Segmentos de la línea
            Gap(20f)    // Epacios entre los segmentos
        )

        // Camino entre los marcadores
        val polylineOptions = PolylineOptions()
            .add(LatLng(43.172993, -2.633388))  // Mikeldi
            .add(LatLng(43.171167, -2.630722))  // Feria
            .add(LatLng(43.168194, -2.628278))  // Sirena
            .add(LatLng(43.168389, -2.631222))  // Basilica
            .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
            .add(LatLng(43.165611, -2.632333))  // Escudo
            .width(15f)  // Grosor de la línea
            .color(android.graphics.Color.RED)  // Color de la línea
            .pattern(pattern) // Patron
            .geodesic(true) // Seguir la curvatura de la tierra

        // Añadir la polyline al mapa
        mMap.addPolyline(polylineOptions)

        // Listener para clics en los marcadores
        mMap.setOnMarkerClickListener { marker ->
            when (marker.tag) {
                "mikeldi" -> abrirActividad(ExplicacionJuegoActivity::class.java,"mikeldi")
                "feria" -> abrirActividad(ExplicacionJuegoActivity::class.java,"feria")
                "sirena" -> abrirActividad(ExplicacionJuegoActivity::class.java,"sirena")
                "basilica" -> abrirActividad(ExplicacionJuegoActivity::class.java,"basilica")
                "personajeArtopila" -> abrirActividad(ExplicacionJuegoActivity::class.java,"personajeArtopila")
                "artopila" -> abrirActividad(ExplicacionJuegoActivity::class.java,"artopila")
                "escudo" -> abrirActividad(ExplicacionJuegoActivity::class.java,"escudo")
                else -> false  // Si no se encuentra el tag, no hace nada
            }
            true
        }

        // Mover la cámara al centro del mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcadorBasilica!!.position, 16f))
    }

    // Función para convertir un Drawable (Vector) en un Bitmap
    private fun getBitmapFromVectorDrawable(drawable: Drawable?): Bitmap {
        val width = drawable?.intrinsicWidth ?: 0
        val height = drawable?.intrinsicHeight ?: 0

        // Crear un bitmap del tamaño adecuado para el drawable
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // Crear un canvas sobre el bitmap
        val canvas = Canvas(bitmap)

        // Establecer el drawable en el canvas
        drawable?.setBounds(0, 0, canvas.width, canvas.height)
        drawable?.draw(canvas)

        return bitmap
    }

    // Función para abrir actividades
    private fun <T> abrirActividad(clase: Class<T>,palabraJuego:String) {
        val intent = Intent(this, clase)
        intent.putExtra(ExplicacionJuegoActivity.palabraJuegoRecivido, palabraJuego)
        startActivity(intent)
    }
}