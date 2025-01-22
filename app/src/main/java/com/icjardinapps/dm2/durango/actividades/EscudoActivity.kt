package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class EscudoActivity : AppCompatActivity() {
    // Crear un array bidimensional de Strings y llenarlo manualmente
    private val arrayBidimensional: Array<Array<String>> = arrayOf(
        arrayOf("S\t", "M\t", "U\t", "R\t", "F\t", "D\t", "M\t", "E\t", "L\t", "L\t"),
        arrayOf("I\t", "N\t", "A\t", "R\t", "U\t", "O\t", "C\t", "A\t", "E\t", "L\t"),
        arrayOf("B\t", "G\t", "A\t", "N\t", "M\t", "R\t", "B\t", "Y\t", "D\t", "U\t"),
        arrayOf("A\t", "L\t", "H\t", "G\t", "A\t", "A\t", "D\t", "R\t", "O\t", "P\t"),
        arrayOf("I\t", "O\t", "Q\t", "A\t", "Z\t", "R\t", "P\t", "Y\t", "R\t", "U\t"),
        arrayOf("A\t", "T\t", "E\t", "I\t", "G\t", "A\t", "I\t", "L\t", "R\t", "S\t"),
        arrayOf("M\t", "N\t", "A\t", "E\t", "O\t", "B\t", "T\t", "A\t", "E\t", "A\t"),
        arrayOf("S\t", "B\t", "U\t", "Y\t", "F\t", "D\t", "E\t", "A\t", "A\t", "R\t"),
        arrayOf("I\t", "A\t", "R\t", "R\t", "I\t", "A\t", "U\t", "O\t", "N\t", "G\t"),
        arrayOf("T\t", "T\t", "A\t", "B\t", "I\t", "R\t", "A\t", "I\t", "P\t", "R\t"),
        arrayOf("S\t", "C\t", "A\t", "U\t", "I\t", "P\t", "E\t", "S\t", "R\t", "U\t")
    )

    // Crear un array de Strings y llenarlo manualmente
    private val arrayPalabras: Array<String> = arrayOf(
        "Tabira",
        "Ibaizabal",
        "Mañaria",
        "Arria",
        "Ibaia",
        "Dorrea",
        "Lupus"
    )

    private lateinit var tvTabira: TextView
    private lateinit var tvIbaizabal: TextView
    private lateinit var tvManaria: TextView
    private lateinit var tvArria: TextView
    private lateinit var tvIbaia: TextView
    private lateinit var tvDorrea: TextView
    private lateinit var tvLupus: TextView
    private lateinit var btnVolverMapa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escudo)
        tvTabira = findViewById(R.id.tabira)
        tvIbaizabal = findViewById(R.id.ibaizabal)
        tvManaria = findViewById(R.id.manaria)
        tvArria = findViewById(R.id.arria)
        tvIbaia = findViewById(R.id.ibaia)
        tvDorrea = findViewById(R.id.dorrea)
        tvLupus = findViewById(R.id.lupus)
        btnVolverMapa = findViewById(R.id.btnVolverMapa)
        btnVolverMapa.setOnClickListener{
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }
        rellenarPalabrasABuscar()
        rellenarSopaDeLetras()
    }

    private fun rellenarPalabrasABuscar() {
        tvTabira.text = arrayPalabras[0]
        tvIbaizabal.text = arrayPalabras[1]
        tvManaria.text = arrayPalabras[2]
        tvArria.text = arrayPalabras[3]
        tvIbaia.text = arrayPalabras[4]
        tvDorrea.text = arrayPalabras[5]
        tvLupus.text = arrayPalabras[6]
    }

    private fun rellenarSopaDeLetras() {

    }
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