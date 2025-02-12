package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

/**
 * Clase que representa la actividad del juego "Artopil", en la que el usuario debe seleccionar la imagen correcta.
 */
class ArtopilActivity : AppCompatActivity() {

    /** Botón para volver al mapa */
    private lateinit var btnVolverMapa: Button

    /** Imagenes de los diferentes alimentos */
    private lateinit var ivArtopil: ImageView
    private lateinit var ivMadalena: ImageView
    private lateinit var ivRoscon: ImageView
    private lateinit var ivBizcocho: ImageView

    /** Texto que muestra las vidas restantes */
    private lateinit var tvVidas: TextView

    /** Imagen correcta que debe seleccionar el usuario */
    private lateinit var ivCorrecto: ImageView

    /** Sonido de error cuando se selecciona una imagen incorrecta */
    private lateinit var sonidoError: MediaPlayer

    /** Contador de vidas restantes */
    private var vidasRestantes: Int = 3

    /**
     * Metodo que se ejecuta al crear la actividad.
     * Inicializa los componentes de la interfaz y establece los listeners.
     * @param savedInstanceState Estado de la actividad si se ha guardado previamente.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artopil)

        // Inicialización de los elementos de la interfaz
        btnVolverMapa = findViewById(R.id.btnVolverMapa)
        ivArtopil = findViewById(R.id.ivArtopil)
        ivMadalena = findViewById(R.id.ivMadalena)
        ivRoscon = findViewById(R.id.ivRoscon)
        ivBizcocho = findViewById(R.id.ivBizcocho)
        tvVidas = findViewById(R.id.tvVidas)

        ivCorrecto = ivArtopil // Imagen correcta que debe seleccionar el usuario
        sonidoError = MediaPlayer.create(this, R.raw.malo) // Carga el sonido de error

        // Configuración del botón para volver al mapa
        btnVolverMapa.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "4")
            startActivity(intent)
        }

        // Asigna el evento de clic a cada imagen
        ivArtopil.setOnClickListener { comprobarImagen(ivArtopil) }
        ivMadalena.setOnClickListener { comprobarImagen(ivMadalena) }
        ivRoscon.setOnClickListener { comprobarImagen(ivRoscon) }
        ivBizcocho.setOnClickListener { comprobarImagen(ivBizcocho) }
    }

    /**
     * Comprueba si la imagen seleccionada es la correcta.
     * Si es correcta, avanza a la actividad de resultados.
     * Si es incorrecta, resta una vida y muestra un mensaje de error.
     * @param iv Imagen seleccionada por el usuario.
     */
    private fun comprobarImagen(iv: ImageView) {
        if (iv == ivCorrecto) {
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra(ResultadosActivity.NOMBREACTIVIDAD, "Artopil")
            startActivity(intent)
        } else {
            vidasRestantes--
            Toast.makeText(this, getString(R.string.errorArtopil), Toast.LENGTH_SHORT).show()
            sonidoError.start()
            actualizarVidas()
            iv.setImageDrawable(null) // Borra la imagen seleccionada incorrectamente
        }
    }

    /**
     * Actualiza el número de vidas restantes en la interfaz.
     */
    private fun actualizarVidas() {
        tvVidas.text = "$vidasRestantes"
    }
}
