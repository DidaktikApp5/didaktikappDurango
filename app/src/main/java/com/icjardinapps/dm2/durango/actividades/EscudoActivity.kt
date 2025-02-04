package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class EscudoActivity : AppCompatActivity() {
    private lateinit var tvTabira: TextView
    private lateinit var tvIbaizabal: TextView
    private lateinit var tvManaria: TextView
    private lateinit var tvArria: TextView
    private lateinit var tvIbaia: TextView
    private lateinit var tvDorrea: TextView
    private lateinit var tvLupus: TextView
    private lateinit var btnVolverMapa: Button
    private lateinit var gridLayout: androidx.gridlayout.widget.GridLayout

    private var lastTouchedView: TextView? = null
    private val selectedViews = mutableListOf<TextView>()
    private var direction: Pair<Int, Int>? = null  // Dirección del movimiento
    private val filas = 12
    private val columnas = 10
    private val palabras = listOf("KOTLIN", "ANDROID", "JAVA", "SOPA", "LETTERS")
    private val letrasSeleccionadas = mutableListOf<String>()

    // Crear un array de Strings y llenarlo manualmente
    private val arrayPalabras = listOf(
        "Tabira",
        "Ibaizabal",
        "Manaria",
        "Arria",
        "Ibaia",
        "Dorrea",
        "Lupus"
    )

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
        gridLayout = findViewById(R.id.gridLayout)
        btnVolverMapa.setOnClickListener{
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }
        rellenarPalabrasABuscar()
        val rowCount = 12 // Número de filas
        val columnCount = 10 // Número de columnas

        for (i in 0 until gridLayout.childCount) {
            val child = gridLayout.getChildAt(i)
            if (child is TextView) {
                val textView = child
                textView.setOnTouchListener { _, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            agregarLetraSeleccionada(textView)
                            lastTouchedView = textView
                            selectedViews.clear()
                            selectedViews.add(textView)
                            direction = null  // Resetear dirección
                            true
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val touchedView = getTouchedView(event.rawX.toInt(), event.rawY.toInt())

                            if (touchedView != null && touchedView != lastTouchedView) {
                                if (direction == null) {
                                    // Establecer dirección en el primer movimiento
                                    direction = calcularDireccion(lastTouchedView, touchedView)
                                    if (direction == null) return@setOnTouchListener true
                                }

                                if (esDireccionValida(lastTouchedView, touchedView)) {
                                    rellenarLetrasIntermedias(lastTouchedView, touchedView)
                                    lastTouchedView = touchedView
                                    selectedViews.add(touchedView)
                                }
                            }
                            true
                        }
                        MotionEvent.ACTION_UP -> {
                            verificarPalabra()
                            lastTouchedView = null
                            selectedViews.clear()
                            direction = null  // Resetear dirección
                            true
                        }
                        else -> false
                    }
                }
            }
        }

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

    private fun calcularDireccion(startView: TextView?, endView: TextView?): Pair<Int, Int>? {
        if (startView == null || endView == null) return null

        val startIndex = gridLayout.indexOfChild(startView)
        val endIndex = gridLayout.indexOfChild(endView)

        if (startIndex == -1 || endIndex == -1) return null  // Evita errores de índice

        val startRow = startIndex / columnas
        val startCol = startIndex % columnas
        val endRow = endIndex / columnas
        val endCol = endIndex % columnas

        val dx = endCol - startCol
        val dy = endRow - startRow

        return when {
            dx == 0 && dy != 0 -> Pair(0, dy / kotlin.math.abs(dy)) // Movimiento vertical
            dy == 0 && dx != 0 -> Pair(dx / kotlin.math.abs(dx), 0) // Movimiento horizontal
            else -> null // Movimiento inválido (diagonal)
        }
    }

    private fun esDireccionValida(startView: TextView?, endView: TextView?): Boolean {
        if (startView == null || endView == null || direction == null) return false

        val startIndex = gridLayout.indexOfChild(startView)
        val endIndex = gridLayout.indexOfChild(endView)

        if (startIndex == -1 || endIndex == -1) return false // Evita errores de índice

        val startRow = startIndex / columnas
        val startCol = startIndex % columnas
        val endRow = endIndex / columnas
        val endCol = endIndex % columnas

        val dx = endCol - startCol
        val dy = endRow - startRow

        // Verificamos si sigue la misma dirección establecida
        val expectedDx = direction!!.first
        val expectedDy = direction!!.second

        return (dx == expectedDx || expectedDx == 0) && (dy == expectedDy || expectedDy == 0)
    }

    private fun getTouchedView(x: Int, y: Int): TextView? {
        try {
            for (i in 0 until gridLayout.childCount) {
                val textView = gridLayout.getChildAt(i) as? TextView ?: continue
                val location = IntArray(2)
                textView.getLocationOnScreen(location)

                val left = location[0]
                val top = location[1]
                val right = left + textView.width
                val bottom = top + textView.height

                if (x in left..right && y in top..bottom) {
                    return textView
                }
            }
        } catch (e: Exception) {
            Log.e("ERROR", "Error en getTouchedView: ${e.message}")
        }
        return null
    }

    private fun rellenarLetrasIntermedias(startView: TextView?, endView: TextView?) {
        if (startView == null || endView == null) return

        val startIndex = gridLayout.indexOfChild(startView)
        val endIndex = gridLayout.indexOfChild(endView)

        val startRow = startIndex / columnas
        val startCol = startIndex % columnas
        val endRow = endIndex / columnas
        val endCol = endIndex % columnas

        val rowStep = if (startRow < endRow) 1 else if (startRow > endRow) -1 else 0
        val colStep = if (startCol < endCol) 1 else if (startCol > endCol) -1 else 0

        var currentRow = startRow
        var currentCol = startCol

        while (currentRow != endRow || currentCol != endCol) {
            val index = currentRow * columnas + currentCol
            val textView = gridLayout.getChildAt(index) as? TextView

            if (textView != null && !selectedViews.contains(textView)) {
                agregarLetraSeleccionada(textView) // Asegurar que todas sean seleccionadas
                selectedViews.add(textView)
            }

            currentRow += rowStep
            currentCol += colStep
        }

        // Agregar la última letra si no está incluida
        if (!selectedViews.contains(endView)) {
            agregarLetraSeleccionada(endView)
            selectedViews.add(endView)
        }
    }

    private fun agregarLetraSeleccionada(textView: TextView) {
        if (textView.text.isNotEmpty()) {
            letrasSeleccionadas.add(textView.text.toString()) // No impedir letras repetidas
            textView.setBackgroundResource(android.R.color.holo_blue_light) // Color de selección
        }
    }

    // Verificar palabras permitiendo repeticiones
    private fun verificarPalabra() {
        val palabraSeleccionada = letrasSeleccionadas.joinToString("")
        if (arrayPalabras.any { it.equals(palabraSeleccionada, ignoreCase = true) }) {
            Toast.makeText(this, "¡Palabra encontrada: $palabraSeleccionada!", Toast.LENGTH_SHORT).show()
            letrasSeleccionadas.clear()
            reiniciarColores()
        } else {
            letrasSeleccionadas.clear()
            reiniciarColores()
        }
    }

    private fun reiniciarColores() {
        for (i in 0 until filas) {
            for (j in 0 until columnas) {
                val textView = gridLayout.getChildAt(i * columnas + j) as? TextView
                textView?.setBackgroundColor(Color.TRANSPARENT) // Fondo transparente
            }
        }
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