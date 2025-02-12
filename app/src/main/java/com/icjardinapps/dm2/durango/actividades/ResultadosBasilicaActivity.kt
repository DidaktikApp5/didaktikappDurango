package com.icjardinapps.dm2.durango.actividades

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

/**
 * Clase para mostrar la ventana de resultados de la actividad Basílica.
 *
 * @author DidaktikAppDurango
 */
class ResultadosBasilicaActivity : AppCompatActivity() {
    /**
     * Ventana de diálogo emergente
     */
    private lateinit var dialog: Dialog

    /**
     * Mensaje de felicitación o ánimos por el puntaje obtenido
     */
    private lateinit var textViewPuntaje: TextView

    /**
     * Puntaje obtenido
     */
    private lateinit var tvPuntajeFinal: TextView

    /**
     * Botón para reintentar o volver al mapa
     */
    private lateinit var btnReintentar: Button

    /**
     * Función que se llama cuando se inicia la actividad.
     * Maneja la funcionalidad del botón para verificar si tienes que repetir
     * la actividad o volver al mapa, además de iniciar la animación de la mascota.
     *
     * @author Julio González
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados_basilica)

        // Inicializar y asociar
        dialog = Dialog(this)
        textViewPuntaje = findViewById(R.id.puntajeFinal)
        tvPuntajeFinal = findViewById(R.id.acertadas)
        btnReintentar = findViewById(R.id.btnFinal)
        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        val puntos = intent.extras?.getInt("finalScore") ?: 0

        // Texto dependiendo de la función del botón
        if(puntos == 4) {
            btnReintentar.text = getString(R.string.volver_mapa)
        } else {
            btnReintentar.text = getString(R.string.reintentar)
        }

        tvPuntajeFinal.text = getString(R.string.puntaje_final, puntos, Preguntas.preguntas.size)

        when (puntos) {
            4 -> textViewPuntaje.text = getString(R.string._4de4)
            3 -> textViewPuntaje.text = getString(R.string._3de4)
            2 -> textViewPuntaje.text = getString(R.string._2de4)
            1 -> textViewPuntaje.text = getString(R.string._1de4)
            else -> textViewPuntaje.text = getString(R.string._0de4)
        }

        btnReintentar.setOnClickListener {
            if(puntos == 4) {
                obtenerPieza()
            } else {
                startActivity(Intent(this@ResultadosBasilicaActivity, BasilicaActivity::class.java))
                finish()
            }
        }
    }

    /**
     * Función que muestra una ventana de diálogo con un mensaje de obtención de pieza.
     *
     * @author Julio González
     */
    private fun obtenerPieza() {
        // Reproduce el sonido de felicitación
        val mediaPlayer = MediaPlayer.create(this, R.raw.felicitacion)
        mediaPlayer.start()

        dialog.setContentView(R.layout.ganar)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnCerrar: Button = dialog.findViewById(R.id.btnGanarCerrar)

        btnCerrar.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            intent.putExtra(MapaActivity.NUMEROACTIVIDAD,"4")
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }
}