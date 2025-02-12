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
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
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
import com.icjardinapps.dm2.durango.actividades.ResultadosActivity.Companion.NOMBREACTIVIDAD
import com.icjardinapps.dm2.durango.databinding.ActivityMapaBinding

/**
 * Actividad del mapa
 *
 * @version 1.0
 * @author DidaktikAppDurango
 */
class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    /**
     * Constantes de la clase
     */
    companion object {
        const val NUMEROACTIVIDAD = "0"
    }

    /**
     * Atributos de la clase
     */
    private lateinit var dialog: Dialog
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaBinding
    private lateinit var numeroActividad: String
    private lateinit var polylineOptionsRed: PolylineOptions
    private lateinit var polylineOptionsGreen: PolylineOptions

    /**
     * onCreate, inicializa la actividad y configura los listeners de los botones.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog = Dialog(this)

        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPuzzleFinal.isEnabled = true

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo para ser usado.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        numeroActividad = intent.getStringExtra(NUMEROACTIVIDAD).toString()
        // Listener para el botón Volver
        binding.btnVolver.setOnClickListener {
            abrirActividad(MainActivity::class.java)
        }

        // Listener para el botón Puzzle Final
        binding.btnPuzzleFinal.isEnabled = true
        binding.btnPuzzleFinal.setOnClickListener {
            if(numeroActividad == "6") {
                abrirActividad(PuzleActivity::class.java)
            } else {
                Toast.makeText(this, getString(R.string.puzzle_final), Toast.LENGTH_SHORT).show()
            }

        }

    }

    /**
     * Para poner en marcha el mapa
     */
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

        if(numeroActividad == "0"){
            polylineOptionsRed = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.RED)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true)
        } else if(numeroActividad == "1"){
            polylineOptionsGreen = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.GREEN)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true) // Seguir la curvatura de la tierra
            polylineOptionsRed = PolylineOptions()
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.RED)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true)
        } else if(numeroActividad == "2"){
            polylineOptionsGreen = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.GREEN)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true) // Seguir la curvatura de la tierra
            polylineOptionsRed = PolylineOptions()
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.RED)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true)
        } else if(numeroActividad == "3"){
            polylineOptionsGreen = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.GREEN)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true) // Seguir la curvatura de la tierra
            polylineOptionsRed = PolylineOptions()
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.RED)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true)
        } else if(numeroActividad == "4"){
            polylineOptionsGreen = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.GREEN)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true) // Seguir la curvatura de la tierra
            polylineOptionsRed = PolylineOptions()
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.RED)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true)
        } else if(numeroActividad == "5"){
            polylineOptionsGreen = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.GREEN)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true) // Seguir la curvatura de la tierra
        } else if(numeroActividad == "6"){
            polylineOptionsGreen = PolylineOptions()
                .add(LatLng(43.172993, -2.633388))  // Mikeldi
                .add(LatLng(43.171167, -2.630722))  // Feria
                .add(LatLng(43.168194, -2.628278))  // Sirena
                .add(LatLng(43.168389, -2.631222))  // Basilica
                .add(LatLng(43.166778, -2.631833))  // Personaje Artopila
                .add(LatLng(43.165611, -2.632333))  // Escudo
                .width(15f)  // Grosor de la línea
                .color(android.graphics.Color.GREEN)  // Color de la línea
                .pattern(pattern) // Patron
                .geodesic(true) // Seguir la curvatura de la tierra
        }
        // Añadir la polyline al mapa
        val numero = numeroActividad.toIntOrNull()
        if(numero != null && numero == 0){
            mMap.addPolyline(polylineOptionsRed)
        } else if (numero != null) {
            if(numero > 4){
                mMap.addPolyline(polylineOptionsGreen)
            } else {
                mMap.addPolyline(polylineOptionsRed)
                mMap.addPolyline(polylineOptionsGreen)
            }
        }


        // Listener para clics en los marcadores
        //Esta parte es para la aplicacion real
        /*mMap.setOnMarkerClickListener { marker ->
            // Obtener el numero de actividad
            if(numeroActividad == "0"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                }
            } else if(numeroActividad == "1"){
                when(marker.tag) {
                    "feria" -> mostrarInfoFeria()
                }
            } else if(numeroActividad == "2"){
                when(marker.tag) {
                    "sirena" -> mostrarInfoSirena()
                }
            } else if(numeroActividad == "3"){
                when(marker.tag) {
                    "basilica" -> mostrarInfoBasilica()
                }
            } else if(numeroActividad == "4"){
                when(marker.tag) {
                    "personajeArtopila" -> mostrarInfoArtopila()
                }
            } else if(numeroActividad == "4.1"){
                when(marker.tag) {
                    "personajeArtopila" -> abrirActividad(PatxikotxuActivity::class.java)
                }
            } else if(numeroActividad == "5"){
                when(marker.tag) {
                    "escudo" -> mostrarInfoEscudo()
                }
            }
            true
        }*/

        // Listener para clics en los marcadores
        //Este trozo es para cuando se haga la presentacion
        mMap.setOnMarkerClickListener { marker ->
            // Obtener el numero de actividad
            if(numeroActividad == "0"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                }
            } else if(numeroActividad == "1"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                    "feria" -> mostrarInfoFeria()
                }
            } else if(numeroActividad == "2"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                    "feria" -> mostrarInfoFeria()
                    "sirena" -> mostrarInfoSirena()
                }
            } else if(numeroActividad == "3"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                    "feria" -> mostrarInfoFeria()
                    "sirena" -> mostrarInfoSirena()
                    "basilica" -> mostrarInfoBasilica()
                }
            } else if(numeroActividad == "4"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                    "feria" -> mostrarInfoFeria()
                    "sirena" -> mostrarInfoSirena()
                    "basilica" -> mostrarInfoBasilica()
                    "personajeArtopila" -> mostrarInfoArtopila()
                }
            } else if(numeroActividad == "5"){
                when(marker.tag) {
                    "mikeldi" -> mostrarInfoMikeldi()
                    "feria" -> mostrarInfoFeria()
                    "sirena" -> mostrarInfoSirena()
                    "basilica" -> mostrarInfoBasilica()
                    "personajeArtopila" -> mostrarInfoArtopila()
                    "escudo" -> mostrarInfoEscudo()
                }
            }
            true
        }
        // Mover la cámara al centro del mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcadorBasilica!!.position, 16f))
    }

    /**
     * Funcion para convertir un Drawable (Vector) en un Bitmap
     */
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

    /**
     * Funcion para abrir actividades
     */
    private fun <T> abrirActividad(clase: Class<T>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    /**
     * Mostrar informacion del juego Mikeldi
     */
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

        // Detener el audio cuando el diálogo se cierra o se va de la actividad
        dialog.setOnDismissListener {
            mediaPlayer.stop() // Detiene la música si el diálogo se cierra
        }

        dialog.show()
    }

    /**
     * Para actualizar el seekBar
     *
     * @param seekBar
     * @param mediaPlayer
     */
    private fun updateSeekBar(seekBar: SeekBar, mediaPlayer: MediaPlayer) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                seekBar.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    /**
     * Mostrar informacion del juego Sirena
     */
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

    /**
     * Función que muestra una ventana de diálogo con la información de la Basilica.
     *
     */
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

    /**
     * Mostrar informacion del juego Feria
     */
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

    /**
     * Mostrar informacion del juego Artopil
     */
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

    /**
     * Mostrar informacion del juego Patxikotxu
     */
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

    /**
     * Mostrar informacion del juego Escudo
     */
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