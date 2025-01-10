package com.icjardinapps.dm2.durango.actividades

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

        // Configuración para las barras del sistema (opcional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de los botones de los altavoces
        findViewById<Button>(R.id.btnAltavoz1).setOnClickListener {
            playAudio(R.raw.sirenafuego) // Reproduce el audio correspondiente
        }

        findViewById<Button>(R.id.btnAltavoz2).setOnClickListener {
            playAudio(R.raw.bonbardeo)
        }

        findViewById<Button>(R.id.btnAltavoz3).setOnClickListener {
            playAudio(R.raw.sirenabombero)
        }

        findViewById<Button>(R.id.btnAltavoz4).setOnClickListener {
            playAudio(R.raw.campanaescuela)
        }

        // Configuración del botón "Comprobar"
        findViewById<Button>(R.id.btnComprobarOpcion).setOnClickListener {
            val selectedOptionId = findViewById<RadioGroup>(R.id.radioGroupOpciones).checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Selecciona una opción", Toast.LENGTH_SHORT).show()
            } else {
                if (selectedOptionId == correctOption) {
                    Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Incorrecto, inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Función para reproducir el audio
    private fun playAudio(resourceId: Int) {
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
