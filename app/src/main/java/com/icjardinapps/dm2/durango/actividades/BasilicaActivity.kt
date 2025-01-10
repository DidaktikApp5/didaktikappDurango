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
    private lateinit var tvPuntaje: TextView
    private lateinit var tvPregunta: TextView
    private lateinit var ivImagen: ImageView
    private lateinit var btnVerdadero: Button
    private lateinit var btnFalso: Button

    private var respuesta: Boolean = false
    private var puntaje: Int = 0
    private var numPregunta: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basilica)

        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        // Inicializar las preguntas
        Preguntas.init(this)

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
                tvPregunta.setTextColor(Color.GREEN)
            } else {
                tvPregunta.setTextColor(Color.RED)
            }

            siguientePreguntaOFinal()
        }

        // Boton Falso
        btnFalso.setOnClickListener {
            if (!respuesta) {
                puntaje++
                actualizarPuntaje(puntaje)
                tvPregunta.setTextColor(Color.GREEN)
            } else {
                tvPregunta.setTextColor(Color.RED)
            }

            siguientePreguntaOFinal()
        }
    }

    private fun actualizarPregunta() {
        btnVerdadero.isEnabled = true
        btnFalso.isEnabled = true
        tvPregunta.setTextColor(Color.BLACK)
        ivImagen.setImageResource(Preguntas.imagenes[numPregunta])
        tvPregunta.text = Preguntas.preguntas[numPregunta]
        respuesta = Preguntas.respuestas[numPregunta]
        numPregunta++
    }

    private fun actualizarPuntaje(punto: Int) {
        tvPuntaje.text = "$puntaje"
    }

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
            }, 1000)
        }
    }
}