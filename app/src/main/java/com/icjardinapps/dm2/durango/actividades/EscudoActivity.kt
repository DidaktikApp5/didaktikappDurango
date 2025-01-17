package com.icjardinapps.dm2.durango.actividades

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class EscudoActivity : AppCompatActivity() {
    /*// Listas para almacenar las palabras y sus lugares correctos
    private val palabras = listOf("palabra1", "palabra2", "palabra3")
    private val lugaresCorrectos = listOf("lugar1", "lugar2", "lugar3")

    // Variables para controlar el estado del juego
    private var palabrasCorrectas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el botón
        val boton = findViewById<Button>(R.id.boton)
        boton.setOnClickListener {
            if (palabrasCorrectas == palabras.size) {
                // Si todas las palabras están en su lugar, el botón se habilita
                boton.isEnabled = true
                boton.setTextColor(Color.BLACK)
            }
        }
    }

    // Función para manejar la acción de arrastrar y soltar
    fun arrastrarYSoltar(view: View) {
        // Se extrae el texto de la palabra arrastrada
        val palabraArrastrada = (view as TextView).text.toString()

        // Buscar el lugar correcto para la palabra
        val lugarCorrecto = lugaresCorrectos[palabras.indexOf(palabraArrastrada)]

        // Buscar el TextView que corresponde al lugar correcto
        val lugarTextView = findViewById<TextView>(resources.getIdentifier(lugarCorrecto, "id", packageName))

        // Si la palabra está en su lugar correcto
        if (lugarTextView.text == palabraArrastrada) {
            // Se pone verde el TextView correspondiente
            lugarTextView.setTextColor(Color.GREEN)
            palabrasCorrectas++ // Incrementar el contador de palabras correctas
        }
    }*/

}