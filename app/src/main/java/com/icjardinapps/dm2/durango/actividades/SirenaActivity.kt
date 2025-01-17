package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class SirenaActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private val correctOption = R.id.rdOpcion2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sirena)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de los botones de los altavoces
        findViewById<Button>(R.id.btnAltavoz1).setOnClickListener {
            reproducirAudio(R.raw.sirenafuego)
        }

        findViewById<Button>(R.id.btnAltavoz2).setOnClickListener {
            reproducirAudio(R.raw.bonbardeo)
        }

        findViewById<Button>(R.id.btnAltavoz3).setOnClickListener {
            reproducirAudio(R.raw.sirenabombero)
        }

        findViewById<Button>(R.id.btnAltavoz4).setOnClickListener {
            reproducirAudio(R.raw.campanaescuela)
        }

        // Configuración del botón "Comprobar"
        findViewById<Button>(R.id.btnComprobarOpcion).setOnClickListener {
            val selectedOptionId = findViewById<RadioGroup>(R.id.radioGroupOpciones).checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, getString(R.string.SirenaselecionaOpcion), Toast.LENGTH_SHORT).show()
            } else {
                if (selectedOptionId == correctOption) {
                    if (::mediaPlayer.isInitialized) {
                        mediaPlayer.release()
                    }

                    val intent = Intent(this, ResultadosActivity::class.java)
                    intent.putExtra(ResultadosActivity.nombreActividad, "Sirena")
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.SirenaIncorrecto), Toast.LENGTH_SHORT).show()
                    reproducirAudio(R.raw.malo)
                }
            }
        }
    }

    // Función para reproducir el audio
    private fun reproducirAudio(resourceId: Int) {
        // Verifica si ya existe un MediaPlayer inicializado, si es así, libéralo
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release() // Libera el reproductor si ya existe
        }

        // Inicializa un nuevo MediaPlayer y comienza la reproducción
        mediaPlayer = MediaPlayer.create(this, resourceId)
        mediaPlayer.start()
    }

    // Asegurarse de liberar los recursos al destruir la actividad
    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
