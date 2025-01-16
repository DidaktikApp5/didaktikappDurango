package com.icjardinapps.dm2.durango.actividades

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.icjardinapps.dm2.durango.R

class ExplicacionJuegoActivity : AppCompatActivity() {

    companion object {
        const val palabraJuegoRecivido = "palabraJuego"
    }

    private lateinit var contenedorContenido: FrameLayout
    private lateinit var seekBar: SeekBar
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicacion_juego)

        contenedorContenido = findViewById(R.id.contenedorContenido)
        seekBar = findViewById(R.id.audioSeekBar)

        val palabraJuego = intent.getStringExtra(palabraJuegoRecivido)
        mostrarContenido(palabraJuego)
    }

    private fun mostrarContenido(palabraJuego: String?) {
        when (palabraJuego) {
            "mikeldi" -> {
                val imageView = ImageView(this)
                imageView.setImageResource(R.drawable.mikeldi)
                contenedorContenido.addView(imageView)
                reproducirAudio(R.raw.mikeldi_explicacion)
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
            "personajeArtopila" -> {
                val imageView = ImageView(this)
                //imageView.setImageResource(R.drawable.tu_imagen) // Reemplaza con tu recurso de imagen
                contenedorContenido.addView(imageView)
            }
            "artopila" -> {
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

    private fun reproducirAudio(audioResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, audioResId)
        seekBar.visibility = SeekBar.VISIBLE
        seekBar.max = mediaPlayer!!.duration

        mediaPlayer?.start()
        isPlaying = true

        // Actualizar SeekBar mientras el audio se reproduce
        Thread {
            while (isPlaying) {
                try {
                    seekBar.progress = mediaPlayer!!.currentPosition
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        // Configurar el listener para la SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress) // Mover el audio a la posición seleccionada por el usuario
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer?.pause() // Pausar el audio cuando empieza a arrastrar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer?.start() // Reanudar el audio cuando suelta la SeekBar
            }
        })

        // Configurar el listener para el fin del audio
        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            seekBar.progress = 0 // Restablecer SeekBar al inicio
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Liberar recursos al destruir la actividad
    }


}
