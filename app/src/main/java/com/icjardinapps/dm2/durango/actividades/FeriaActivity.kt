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

/**
 * Actividad que representa un juego de arrastrar y soltar objetos en una feria.
 *
 * El usuario debe arrastrar imágenes de elementos a una bolsa. Si los objetos son correctos,
 * desaparecen y, al completar todos los elementos correctos, se redirige a la pantalla de resultados.
 *
 * @author Mikel Ramos
 *
 * En caso de error, se reproduce un sonido y se restablecen las imágenes.
 */
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

    /**
     * Metodo llamado al crear la actividad.
     *
     * Se inicializan las vistas y se configuran los eventos de arrastre y soltar.
     *
     * @param savedInstanceState Estado de la actividad en caso de recreación.
     */
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

                            val intent = Intent(this, ResultadosActivity::class.java)
                            intent.putExtra(ResultadosActivity.NOMBREACTIVIDAD, "Feria")
                            startActivity(intent)
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

    /**
     * Configura el evento de arrastrar para un `ImageView`.
     *
     * Al detectar un toque sobre la imagen, se inicia un evento de arrastre con su identificador.
     *
     * @param imageView Imagen que será arrastrada.
     */
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

    /**
     * Restablece la visibilidad de todas las imágenes y reinicia el contador de aciertos.
     */
    private fun resetImagenes() {
        ets.visibility = View.VISIBLE
        libro.visibility = View.VISIBLE
        goazen.visibility = View.VISIBLE
        esparrago.visibility = View.VISIBLE
        zapatos.visibility = View.VISIBLE
        hamburguesa.visibility = View.VISIBLE
        countCorrectas = 0
    }

    /**
     * Reproduce un archivo de audio.
     *
     * Si ya hay un `MediaPlayer` en uso, se libera antes de crear uno nuevo.
     *
     * @param resourceId ID del recurso de audio a reproducir.
     */
    private fun reproducirAudio(resourceId: Int) {
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release() // Libera el reproductor si ya existe
        }

        mediaPlayer = MediaPlayer.create(this, resourceId)
        mediaPlayer.start()
    }

    /**
     * Libera los recursos del `MediaPlayer` cuando la actividad se destruye.
     */
    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
