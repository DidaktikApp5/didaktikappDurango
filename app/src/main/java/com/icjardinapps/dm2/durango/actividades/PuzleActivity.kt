package com.icjardinapps.dm2.durango.actividades

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class PuzleActivity : AppCompatActivity() {
    // Referencias a las imágenes
    private lateinit var pieza1: ImageView
    private lateinit var pieza2: ImageView
    private lateinit var pieza3: ImageView
    private lateinit var pieza4: ImageView
    private lateinit var pieza5: ImageView
    private lateinit var pieza6: ImageView
    private lateinit var pieza7: ImageView
    private lateinit var pieza1Final: ImageView
    private lateinit var pieza2Final: ImageView
    private lateinit var pieza3Final: ImageView
    private lateinit var pieza4Final: ImageView
    private lateinit var pieza5Final: ImageView
    private lateinit var pieza6Final: ImageView
    private lateinit var pieza7Final: ImageView
    private var remainingPairs = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzle)

        pieza1 = findViewById(R.id.piece1)
        pieza2 = findViewById(R.id.piece2)
        pieza3 = findViewById(R.id.piece3)
        pieza4 = findViewById(R.id.piece4)
        pieza5 = findViewById(R.id.piece5)
        pieza6 = findViewById(R.id.piece6)
        pieza7 = findViewById(R.id.piece7)
        pieza1Final = findViewById(R.id.piece1Puzzle)
        pieza2Final = findViewById(R.id.piece2Puzzle)
        pieza3Final = findViewById(R.id.piece3Puzzle)
        pieza4Final = findViewById(R.id.piece4Puzzle)
        pieza5Final = findViewById(R.id.piece5Puzzle)
        pieza6Final = findViewById(R.id.piece6Puzzle)
        pieza7Final = findViewById(R.id.piece7Puzzle)

        // Configuramos el arrastre
        setDraggable(pieza1)
        setDraggable(pieza2)
        setDraggable(pieza3)
        setDraggable(pieza4)
        setDraggable(pieza5)
        setDraggable(pieza6)
        setDraggable(pieza7)

        // Configuramos los receptores
        setDroppable(pieza1Final, pieza1)
        setDroppable(pieza2Final, pieza2)
        setDroppable(pieza3Final, pieza3)
        setDroppable(pieza4Final, pieza4)
        setDroppable(pieza5Final, pieza5)
        setDroppable(pieza6Final, pieza6)
        setDroppable(pieza7Final, pieza7)
    }

    /**
     * Hace que una imagen sea arrastrable.
     *
     * @author Julio Gonzalez
     * @param view la vista de la imagen que será arrastrable.
     */
    private fun setDraggable(view: ImageView) {
        view.setOnTouchListener { v, event ->
            val clipData = ClipData.newPlainText("", "")
            val shadow = View.DragShadowBuilder(v)
            v.startDragAndDrop(clipData, shadow, v, 0)
            true
        }
    }

    /**
     * Configura un área de destino para aceptar elementos arrastrados.
     *
     * @author Julio Gonzalez
     * @param target la imagen que aceptará el elemento arrastrado.
     * @param matchingView la vista de la imagen que debe ser arrastrada y coincidir.
     */
    private fun setDroppable(target: ImageView, matchingView: ImageView) {
        target.setOnDragListener { v, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DROP -> {
                    val draggedView = event.localState as? View
                    if (draggedView == matchingView) {
                        // Si coincide, ocultamos la imagen
                        draggedView?.visibility = View.GONE
                        v.alpha = 1.0f
                        remainingPairs--
                        checkCompletion()
                    }
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Verifica si todos los pares han sido emparejados correctamente.
     * Si no quedan pares, finaliza la actividad.
     *
     * @author Julio Gonzalez
     */
    private fun checkCompletion() {
        if (remainingPairs == 0) {
            val intent = Intent(this, DespedidaActivity::class.java)
            startActivity(intent)
        }
    }
}