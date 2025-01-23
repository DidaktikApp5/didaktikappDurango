package com.icjardinapps.dm2.durango.actividades

import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class PuzleActivity : AppCompatActivity() {
    // Posiciones correctas de las piezas en el puzzle
    private val correctPositions = mapOf(
        R.id.piece1 to Pair(100f, 100f),
        R.id.piece2 to Pair(200f, 100f),
        R.id.piece3 to Pair(300f, 100f),
        R.id.piece4 to Pair(100f, 200f),
        R.id.piece5 to Pair(200f, 200f),
        R.id.piece6 to Pair(300f, 200f),
        R.id.piece7 to Pair(200f, 300f)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzle)

        // Inicializar las piezas
        val piece1 = findViewById<ImageView>(R.id.piece1)
        val piece2 = findViewById<ImageView>(R.id.piece2)
        val piece3 = findViewById<ImageView>(R.id.piece3)
        val piece4 = findViewById<ImageView>(R.id.piece4)
        val piece5 = findViewById<ImageView>(R.id.piece5)
        val piece6 = findViewById<ImageView>(R.id.piece6)
        val piece7 = findViewById<ImageView>(R.id.piece7)

        // Zona de puzzle donde las piezas serán soltadas
        val zonePuzzle = findViewById<View>(R.id.zonePuzzle)

        // Configurar las piezas para arrastrarlas
        val pieces = listOf(piece1, piece2, piece3, piece4, piece5, piece6, piece7)
        pieces.forEach { piece ->
            piece.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val shadow = View.DragShadowBuilder(v)
                        v.startDragAndDrop(null, shadow, v, 0)
                        true
                    }
                    else -> false
                }
            }
        }

        // Configurar la zona para recibir las piezas
        zonePuzzle.setOnDragListener { _, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val draggedView = event.localState as View

                    // Obtener las coordenadas donde la pieza fue soltada
                    val dropX = event.x
                    val dropY = event.y

                    // Verificar si la pieza está cerca de la posición correcta
                    val correctPosition = correctPositions[draggedView.id]
                    if (correctPosition != null && isPieceInCorrectPosition(dropX, dropY, correctPosition)) {
                        // Si está correctamente colocada
                        Toast.makeText(this, "¡Pieza colocada correctamente!", Toast.LENGTH_SHORT).show()
                        draggedView.visibility = View.INVISIBLE  // Ocultar la pieza cuando se coloque correctamente
                    } else {
                        // Si no está correctamente colocada, devolver la pieza a su posición original
                        draggedView.layout(
                            draggedView.left,
                            draggedView.top,
                            draggedView.right,
                            draggedView.bottom
                        )
                    }
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Verifica si la pieza se encuentra cerca de la posición correcta.
     *
     * Compara la posición actual de la pieza (x, y) con la posición correcta,
     * permitiendo una tolerancia de 50 unidades en cada dirección.
     *
     * @author Julio González
     * @param x La posición x actual de la pieza.
     * @param y La posición y actual de la pieza.
     * @param correctPosition La posición correcta de la pieza (x, y).
     * @return True si la pieza se encuentra cerca de la posición correcta, false en caso contrario.
     */
    private fun isPieceInCorrectPosition(x: Float, y: Float, correctPosition: Pair<Float, Float>): Boolean {
        val tolerance = 50f  // Tolerancia para la comparación de posiciones

        return (x in correctPosition.first - tolerance..correctPosition.first + tolerance) &&
                (y in correctPosition.second - tolerance..correctPosition.second + tolerance)
    }
}