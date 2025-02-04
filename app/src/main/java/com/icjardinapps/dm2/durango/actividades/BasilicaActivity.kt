package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class BasilicaActivity : AppCompatActivity() {
    /**
     * Texto de mensaje de mascota.
     */
    private lateinit var mensajeMascota: TextView

    /**
     * Texto de puntaje.
     */
    private lateinit var tvPuntaje: TextView

    /**
     * Texto de pregunta.
     */
    private lateinit var tvPregunta: TextView

    /**
     * Imagen para cada pregunta.
     */
    private lateinit var ivImagen: ImageView

    /**
     * Botón de verdadero.
     */
    private lateinit var btnVerdadero: Button

    /**
     * Botón de falso.
     */
    private lateinit var btnFalso: Button

    /**
     * Verificación de respuesta.
     */
    private var respuesta: Boolean = false

    /**
     * Puntaje.
     */
    private var puntaje: Int = 0

    /**
     * Número de pregunta.
     */
    private var numPregunta: Int = 0

    /**
     * Al iniciar la aplicación. Maneja la lógica del juego de verdadero o falso.
     * Si se responde correctámente da un mensaje de 'correcto' y la pregunta se pone verde,
     * en caso contrario mensaje de 'incorrecto' y el texto en rojo.
     *
     * @author Julio González
     * @param savedInstanceState Estado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basilica)

        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)
        mensajeMascota = findViewById<TextView>(R.id.mensajeMascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        // Inicializar las preguntas
        Preguntas.init(this)

        // Asociar con el xml
        tvPuntaje = findViewById(R.id.puntos)
        ivImagen = findViewById(R.id.imageViewImagen)
        tvPregunta = findViewById(R.id.tvPregunta)
        btnVerdadero = findViewById(R.id.buttonVerdadero)
        btnFalso = findViewById(R.id.buttonFalso)

        actualizarPregunta()

        // Boton Verdadero
        btnVerdadero.setOnClickListener {
            if (respuesta) {
                puntaje++
                actualizarPuntaje(puntaje)
                tvPregunta.setBackgroundResource(R.color.verde_claro)
                mensajeMascota.text = getString(R.string.correcto)
            } else {
                mensajeMascota.text = getString(R.string.incorrecto)
                tvPregunta.setBackgroundResource(R.color.rojo)
            }

            siguientePreguntaOFinal()
        }

        // Boton Falso
        btnFalso.setOnClickListener {
            if (!respuesta) {
                puntaje++
                actualizarPuntaje(puntaje)
                tvPregunta.setBackgroundResource(R.color.verde_claro)
                mensajeMascota.text = getString(R.string.correcto)
            } else {
                tvPregunta.setBackgroundResource(R.color.rojo)
                mensajeMascota.text = getString(R.string.incorrecto)
            }

            siguientePreguntaOFinal()
        }
    }

    /**
     * Habilita los botones.
     * Vuelve a poner el texto de color negro y muestra la siguiente pregunta.
     *
     * @author Julio González
     */
    private fun actualizarPregunta() {
        btnVerdadero.isEnabled = true
        btnFalso.isEnabled = true
        tvPregunta.setBackgroundColor(Color.TRANSPARENT)
        mensajeMascota.text = ""
        ivImagen.setImageResource(Preguntas.imagenes[numPregunta])
        tvPregunta.text = Preguntas.preguntas[numPregunta]
        respuesta = Preguntas.respuestas[numPregunta]
        numPregunta++
    }

    /**
     * Actualiza el puntaje actual obtenido.
     *
     * @author Julio González
     * @param punto Puntaje obtenido
     */
    private fun actualizarPuntaje(punto: Int) {
        tvPuntaje.text = "$puntaje"
    }

    /**
     * Verifica si hay preguntas por salir o si es la ultima pregunta.
     * Añade un delay de 1.5s antes de pasar a la siguiente pregunta.
     * Deshabilita los botones para no ser pulsados mientras cambia de pregunta.
     *
     * @author Julio González
     */
    private fun siguientePreguntaOFinal() {
        if (numPregunta == Preguntas.preguntas.size) {
            val intent = Intent(this, ResultadosBasilicaActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("finalScore", puntaje)
            intent.putExtras(bundle)
            finish()
            startActivity(intent)
        } else {
            btnVerdadero.isEnabled = false
            btnFalso.isEnabled = false

            Handler().postDelayed({
                actualizarPregunta()
            }, 1500)
        }
    }
}