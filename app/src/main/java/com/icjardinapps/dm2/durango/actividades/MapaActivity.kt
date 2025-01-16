package com.icjardinapps.dm2.durango.actividades

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.VideoView
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
    private lateinit var dialog: Dialog
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = Dialog(this)

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
        val marcadorMikeldi = mMap.addMarker(MarkerOptions()
            .position(LatLng(43.172993, -2.633388))
            .icon(BitmapDescriptorFactory.fromBitmap(iconBitmapMikeldi)))
        marcadorMikeldi?.tag = "mikeldi"

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
                "mikeldi" -> mostrarInfoMikeldi()
                "feria" -> mostrarInfoFeria()
                "sirena" -> mostrarInfoSirena()
                "basilica" -> mostrarInfoBasilica()
                "personajeArtopila" -> mostrarInfoPatxikotxu()
                "artopila" -> mostrarInfoArtopila()
                "escudo" -> mostrarInfoEscudo()
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
    private fun <T> abrirActividad(clase: Class<T>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun mostrarInfoMikeldi() {
        dialog.setContentView(R.layout.info_mikeldi)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoMikeldi)
        val seekBar: SeekBar = dialog.findViewById(R.id.seekBarAudio)
        val mediaPlayer = MediaPlayer.create(this, R.raw.mikeldi_explicacion)

        mediaPlayer.setOnPreparedListener {
            seekBar.max = mediaPlayer.duration
            mediaPlayer.start() // Inicia la reproducción cuando el MediaPlayer está preparado
            updateSeekBar(seekBar, mediaPlayer)
        }

        // Actualización del SeekBar
        mediaPlayer.setOnCompletionListener {
            seekBar.progress = 0
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                mediaPlayer.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                mediaPlayer.start()
            }
        })

        btnComenzar.setOnClickListener {
            abrirActividad(MikeldiActivity::class.java)
            mediaPlayer.stop() // Detén el MediaPlayer al cambiar de actividad
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateSeekBar(seekBar: SeekBar, mediaPlayer: MediaPlayer) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                seekBar.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }




    private fun mostrarInfoSirena() {
        dialog.setContentView(R.layout.info_sirena)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoSirena)

        btnComenzar.setOnClickListener {
            abrirActividad(SirenaActivity::class.java)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarInfoBasilica() {
        dialog.setContentView(R.layout.info_basilica)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoBasilica)

        btnComenzar.setOnClickListener {
            abrirActividad(BasilicaActivity::class.java)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarInfoFeria() {
        dialog.setContentView(R.layout.info_feria)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Obtener el VideoView del layout
        val videoView: VideoView = dialog.findViewById(R.id.videoViewInfoFeria)

        // Establecer la ruta del video
        val uriPath = "android.resource://" + packageName + "/" + R.raw.feria_explicacion
        videoView.setVideoURI(Uri.parse(uriPath))

        // Habilitar los controles del video
        videoView.setMediaController(android.widget.MediaController(this))
        videoView.setMediaController(android.widget.MediaController(this).apply {
            setAnchorView(videoView)  // Ancla los controles al VideoView
        })

        // Iniciar el video
        videoView.start()

        // Obtener el botón y configurar su acción
        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoFeria)
        btnComenzar.setOnClickListener {
            abrirActividad(FeriaActivity::class.java)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarInfoArtopila() {
        dialog.setContentView(R.layout.info_artopila)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Obtener el VideoView del layout
        val videoView: VideoView = dialog.findViewById(R.id.videoViewInfoArtopila)

        // Establecer la ruta del video
        val uriPath = "android.resource://" + packageName + "/" + R.raw.artopila_explicacion
        videoView.setVideoURI(Uri.parse(uriPath))

        // Habilitar los controles del video
        videoView.setMediaController(android.widget.MediaController(this))
        videoView.setMediaController(android.widget.MediaController(this).apply {
            setAnchorView(videoView)  // Ancla los controles al VideoView
        })

        // Iniciar el video
        videoView.start()

        // Obtener el botón y configurar su acción
        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoArtopila)
        btnComenzar.setOnClickListener {
            abrirActividad(ArtopilActivity::class.java)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarInfoPatxikotxu() {
        dialog.setContentView(R.layout.info_patxikotxu)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Obtener el VideoView del layout
        val videoView: VideoView = dialog.findViewById(R.id.videoViewInfoPatxikotxu)

        // Establecer la ruta del video
        val uriPath = "android.resource://" + packageName + "/" + R.raw.patxikotxu_explicacion
        videoView.setVideoURI(Uri.parse(uriPath))

        // Habilitar los controles del video
        videoView.setMediaController(android.widget.MediaController(this))
        videoView.setMediaController(android.widget.MediaController(this).apply {
            setAnchorView(videoView)  // Ancla los controles al VideoView
        })

        // Iniciar el video
        videoView.start()

        // Obtener el botón y configurar su acción
        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoPatxikotxu)
        btnComenzar.setOnClickListener {
            abrirActividad(PatxikotxuActivity::class.java)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarInfoEscudo() {
        dialog.setContentView(R.layout.info_escudo)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Obtener el VideoView del layout
        val videoView: VideoView = dialog.findViewById(R.id.videoViewInfoEscudo)

        // Establecer la ruta del video
        val uriPath = "android.resource://" + packageName + "/" + R.raw.escudo_explicacion
        videoView.setVideoURI(Uri.parse(uriPath))

        // Habilitar los controles del video
        videoView.setMediaController(android.widget.MediaController(this))
        videoView.setMediaController(android.widget.MediaController(this).apply {
            setAnchorView(videoView)  // Ancla los controles al VideoView
        })

        // Iniciar el video
        videoView.start()

        // Obtener el botón y configurar su acción
        val btnComenzar: Button = dialog.findViewById(R.id.btnInfoEscudo)
        btnComenzar.setOnClickListener {
            abrirActividad(EscudoActivity::class.java)
            dialog.dismiss()
        }

        dialog.show()
    }

}