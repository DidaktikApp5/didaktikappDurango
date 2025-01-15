package com.icjardinapps.dm2.durango.actividades

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class PatxikotxuActivity : AppCompatActivity() {
    // Variables para el audio y la comprobación
    private lateinit var sonidoError: MediaPlayer
    private lateinit var sonidoCorrecto: MediaPlayer
    private var nombreCorrecto: String = "NOMBRE_CORRECTO" // Define el nombre correcto aquí
    private var intentos: Int = 0

    // Variables para los elementos de la interfaz
    private lateinit var tvNombre: TextView
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patxikotxu) // Crea la interfaz con el layout activity_patxikotxu.xml

        // Inicializa los elementos de la interfaz
        tvNombre = findViewById(R.id.tvNombre)
        btnVolver = findViewById(R.id.btnVolver)

        // Inicializa el audio de error y correcto
        sonidoError = MediaPlayer.create(this, R.raw.error) // R.raw.error es el nombre del archivo de audio de error
        sonidoCorrecto = MediaPlayer.create(this, R.raw.correcto) // R.raw.correcto es el nombre del archivo de audio de correcto

        // Configura el comportamiento del botón volver
        btnVolver.setOnClickListener {
            finish() // Cierra la activity actual
        }

        // Configura el comportamiento del TextView para el nombre
        tvNombre.setOnClickListener {
            comprobarNombre()
        }
    }

    // Método para comprobar el nombre
    private fun comprobarNombre() {
        intentos++

        // Comprueba si el nombre es correcto
        if (tvNombre.text.toString() == nombreCorrecto) {
            // Si el nombre es correcto, reproduce el audio de correcto, pinta el texto de verde
            sonidoCorrecto.start()
            tvNombre.setTextColor(resources.getColor(R.color.verde)) // R.color.verde es el ID del color verde tvNombre.text = "¡Nombre Correcto!" // Muestra un mensaje de éxito
        } else {
            // Si el nombre es incorrecto, reproduce el audio de error, pinta el texto de rojo
            sonidoError.start()
            tvNombre.setTextColor(resources.getColor(R.color.rojo)) // R.color.rojo es el ID del color rojo
            tvNombre.text = "Intenta de nuevo" // Muestra un mensaje de error
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Libera los recursos de audio al destruir la activity
        sonidoError.release()
        sonidoCorrecto.release()
    }

}