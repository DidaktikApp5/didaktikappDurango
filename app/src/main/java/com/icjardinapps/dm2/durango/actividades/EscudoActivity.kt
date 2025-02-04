package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    private val letrasSeleccionadas = mutableListOf<String>()
    private val palabrasCorrectasUsuario = mutableListOf<String>()
    private lateinit var sonidoError: MediaPlayer

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
        sonidoError = MediaPlayer.create(this, R.raw.malo)
        btnVolverMapa.setOnClickListener{
            val intent = Intent(this, MapaActivity::class.java)
            intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "5")
            startActivity(intent)
        }
        rellenarPalabrasABuscar()

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
                            verificarJuegoCompletado()
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

    private fun verificarJuegoCompletado() {
        if(palabrasCorrectasUsuario.size == 7){
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra(ResultadosActivity.NOMBREACTIVIDAD, "Escudo")
            startActivity(intent)
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
        if (textView.text.isNotEmpty() && !selectedViews.contains(textView)) {
            letrasSeleccionadas.add(textView.text.toString())
            selectedViews.add(textView)
            textView.setBackgroundResource(R.color.azul_claro) // Cambia el color temporalmente
        }
    }

    private fun verificarPalabra() {
        val palabraSeleccionada = letrasSeleccionadas.joinToString("")

        if (arrayPalabras.any { it.equals(palabraSeleccionada, ignoreCase = true) }) {
            // Mantener el color azul si la palabra es correcta
            selectedViews.forEach { it.setBackgroundResource(R.color.azul_claro) }

            when {
                tvArria.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvArria.paintFlags = tvArria.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvArria.text.toString())
                    tvArria.setBackgroundResource(R.color.verde_claro)
                }
                tvIbaia.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvIbaia.paintFlags = tvIbaia.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvIbaia.text.toString())
                    tvIbaia.setBackgroundResource(R.color.verde_claro)
                }
                tvDorrea.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvDorrea.paintFlags = tvDorrea.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvDorrea.text.toString())
                    tvDorrea.setBackgroundResource(R.color.verde_claro)
                }
                tvLupus.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvLupus.paintFlags = tvLupus.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvLupus.text.toString())
                    tvLupus.setBackgroundResource(R.color.verde_claro)
                }
                tvTabira.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvTabira.paintFlags = tvTabira.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvTabira.text.toString())
                    tvTabira.setBackgroundResource(R.color.verde_claro)
                }
                tvIbaizabal.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvIbaizabal.paintFlags = tvIbaizabal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvIbaizabal.text.toString())
                    tvIbaizabal.setBackgroundResource(R.color.verde_claro)
                }
                tvManaria.text.contentEquals(palabraSeleccionada, ignoreCase = true) -> {
                    tvManaria.paintFlags = tvManaria.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    palabrasCorrectasUsuario.add(tvManaria.text.toString())
                    tvManaria.setBackgroundResource(R.color.verde_claro)
                }
            }
        } else {
            // Si la palabra es incorrecta, restablecer los colores de los seleccionados
            selectedViews.forEach { it.setBackgroundColor(Color.TRANSPARENT) }
            sonidoError.start()
        }

        letrasSeleccionadas.clear()
        selectedViews.clear()
    }

    private fun reiniciarColores() {
        val azulClaro = ContextCompat.getColor(this, R.color.azul_claro) // Obtener el color real

        for (i in 0 until gridLayout.childCount) {
            val textView = gridLayout.getChildAt(i) as? TextView
            if (textView != null) {
                val background = textView.background
                if (background is ColorDrawable && background.color == azulClaro) {
                    // Si el fondo es azul claro, no cambiarlo
                    continue
                }
                textView.setBackgroundColor(Color.TRANSPARENT) // Restablecer los demás a transparente
            }
        }
    }

}