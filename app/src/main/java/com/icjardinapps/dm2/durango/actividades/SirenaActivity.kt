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

/**
 * Actividad que presenta un juego en el que el usuario debe seleccionar el sonido correspondiente
 * de una serie de opciones de acuerdo con los sonidos de sirenas y otros ruidos.
 *
 * @author Mikel Ramos
 */
class SirenaActivity : AppCompatActivity() {

    /**
     * Atributos de la clase
     */
    private lateinit var mediaPlayer: MediaPlayer
    private val correctOption = R.id.rdOpcion2

    /**
     * Se ejecuta cuando se crea la actividad. Inicializa la interfaz de usuario, configura los
     * botones y asigna las funciones para reproducir los audios y comprobar las respuestas.
     *
     * @param savedInstanceState El estado guardado de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sirena)

        // Configura los márgenes de la interfaz para que no se tapen con las barras del sistema
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
                    // Si la opción es correcta, liberar recursos y abrir la actividad de resultados
                    if (::mediaPlayer.isInitialized) {
                        mediaPlayer.release()
                    }

                    val intent = Intent(this, ResultadosActivity::class.java)
                    intent.putExtra(ResultadosActivity.NOMBREACTIVIDAD, "Sirena")
                    startActivity(intent)
                } else {
                    // Si la opción es incorrecta, mostrar mensaje y reproducir sonido de error
                    Toast.makeText(this, getString(R.string.SirenaIncorrecto), Toast.LENGTH_SHORT).show()
                    reproducirAudio(R.raw.malo)
                }
            }
        }
    }

    /**
     * Reproduce el audio correspondiente al identificador de recurso proporcionado.
     * Si ya hay un MediaPlayer en uso, lo libera antes de inicializar uno nuevo.
     *
     * @param resourceId El identificador del recurso de audio que se reproducirá.
     */
    private fun reproducirAudio(resourceId: Int) {
        // Verifica si ya existe un MediaPlayer inicializado, si es así, libéralo
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release() // Libera el reproductor si ya existe
        }

        // Inicializa un nuevo MediaPlayer y comienza la reproducción
        mediaPlayer = MediaPlayer.create(this, resourceId)
        mediaPlayer.start()
    }

    /**
     * Libera los recursos utilizados por el MediaPlayer cuando la actividad se destruye.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
