package com.icjardinapps.dm2.durango.actividades

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.icjardinapps.dm2.durango.R

class ExplicacionJuegoActivity : AppCompatActivity() {

    private lateinit var contenedorContenido: ConstraintLayout
    private var palabraClave: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicacion_juego)

        contenedorContenido = findViewById(R.id.contenedorContenido)
        palabraClave = intent.getStringExtra("palabraClave")

        mostrarContenido(palabraClave)
    }

    private fun mostrarContenido(palabraClave: String?) {
        when (palabraClave) {
            "mikeldi" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }
            "feria" -> {
                val videoView = VideoView(this)
                //val uriPath = "android.resource://" + packageName + "/" + R.raw.tu_video // Reemplaza con tu archivo de video
                //videoView.setVideoURI(Uri.parse(uriPath))
                videoView.start()
                contenedorContenido.addView(videoView)
            }
            "sirena" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }
            "basilica" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }
            "patxikotxu" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }
            "artopil" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }
            "escudo" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }

        }
    }
}
