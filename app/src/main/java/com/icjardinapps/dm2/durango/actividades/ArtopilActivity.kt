package com.icjardinapps.dm2.durango.actividades

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class ArtopilActivity : AppCompatActivity() {
    // Variables para la lógica del juego
    private var vidasRestantes = 3
    private var piezasCorrectas = 0
    private val piezasPuzzle = listOf(
        R.drawable.pieza1,
        R.drawable.pieza2,
        R.drawable.pieza3,
        R.drawable.pieza4
    )
    private val imagenesIncorrectas = listOf(
        R.drawable.imagen_incorrecta1,
        R.drawable.imagen_incorrecta2,
        R.drawable.imagen_incorrecta3
    )

    // Vistas del layout
    private lateinit var tvVidas: TextView
    private lateinit var ivPuzzle: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artopil)

        // Inicializar las vistas
        tvVidas = findViewById(R.id.tvVidas)
        ivPuzzle = findViewById(R.id.ivPuzzle)

        // Mostrar las vidas iniciales
        actualizarVidas()

        // Establecer un listener para la imagen del rompecabezas
        ivPuzzle.setOnClickListener {
            // Obtener una imagen aleatoria del rompecabezas o una incorrecta
            val imagen = if (piezasCorrectas < piezasPuzzle.size) {
                piezasPuzzle.random()
            } else {
                imagenesIncorrectas.random()
            }
            ivPuzzle.setImageResource(imagen)

            // Comprobar si la imagen es correcta o incorrecta
            if (imagen in piezasPuzzle) {
                // La imagen es correcta
                piezasCorrectas++
                Toast.makeText(this, "Correcto!", Toast.LENGTH_SHORT).show()
            } else {
                // La imagen es incorrecta
                vidasRestantes--
                Toast.makeText(this, "In correcto! Vidas restantes: $vidasRestantes", Toast.LENGTH_SHORT).show()
                if (vidasRestantes <= 0) {
                    Toast.makeText(this, "Juego terminado!", Toast.LENGTH_LONG).show()
                    // Aquí puedes agregar lógica para reiniciar el juego o finalizar la actividad
                }
            }
            actualizarVidas()
        }
    }

    // Función para actualizar el número de vidas en la interfaz
    private fun actualizarVidas() {
        tvVidas.text = "Vidas: $vidasRestantes"
    }

}