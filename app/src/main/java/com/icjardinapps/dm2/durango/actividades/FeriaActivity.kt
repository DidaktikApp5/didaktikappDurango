package com.icjardinapps.dm2.durango.actividades

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.Toast
import com.icjardinapps.dm2.durango.R

class FeriaActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var bolsa: ImageView
    private lateinit var ets: ImageView
    private lateinit var libro: ImageView
    private lateinit var goazen: ImageView
    private lateinit var esparrago: ImageView
    private lateinit var zapatos: ImageView
    private lateinit var hamburguesa: ImageView

    private val imagenesCorrectas = listOf("ets", "libro", "goazen")
    private var countCorrectas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feria)

        bolsa = findViewById(R.id.bolsa)
        ets = findViewById(R.id.ets)
        libro = findViewById(R.id.libro)
        goazen = findViewById(R.id.goazen)
        esparrago = findViewById(R.id.esparrago)
        zapatos = findViewById(R.id.zapatos)
        hamburguesa = findViewById(R.id.hamburguesa)

        setDragListener(ets)
        setDragListener(libro)
        setDragListener(goazen)
        setDragListener(esparrago)
        setDragListener(zapatos)
        setDragListener(hamburguesa)

        bolsa.setOnDragListener { view, event ->
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    val itemTag = event.clipData.getItemAt(0).text.toString()
                    if (imagenesCorrectas.contains(itemTag)) {
                        findViewById<ImageView>(resources.getIdentifier(itemTag, "id", packageName)).visibility = View.GONE
                        countCorrectas++
                        if (countCorrectas == imagenesCorrectas.size) {
                            if (::mediaPlayer.isInitialized) {
                                mediaPlayer.release()
                            }

                            //val intent = Intent(this, ResultadosActivity::class.java)
                            //startActivity(intent)
                        }
                    } else {
                        reproducirAudio(R.raw.malo)
                        Toast.makeText(this, getString(R.string.FeriaIncorrecto), Toast.LENGTH_SHORT).show()
                        resetImagenes()

                    }
                    true
                }
                else -> true
            }
        }
    }

    private fun setDragListener(imageView: ImageView) {
        imageView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val item = ClipData.Item(view.tag.toString())
                    val dragData = ClipData(
                        view.tag.toString(),
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        item
                    )
                    val shadow = View.DragShadowBuilder(view)
                    view.startDragAndDrop(dragData, shadow, null, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun resetImagenes() {
        ets.visibility = View.VISIBLE
        libro.visibility = View.VISIBLE
        goazen.visibility = View.VISIBLE
        esparrago.visibility = View.VISIBLE
        zapatos.visibility = View.VISIBLE
        hamburguesa.visibility = View.VISIBLE
        countCorrectas = 0
    }

    // Función para reproducir el audio
    private fun reproducirAudio(resourceId: Int) {
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
