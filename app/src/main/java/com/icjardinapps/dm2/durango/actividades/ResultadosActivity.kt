package com.icjardinapps.dm2.durango.actividades

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class ResultadosActivity : AppCompatActivity() {

    companion object {
        const val NOMBREACTIVIDAD = "nombreJuego"
    }

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        dialog = Dialog(this)

        val txtAcierto: TextView = findViewById(R.id.txtAcierto)
        val buttonVolverMapa: Button = findViewById(R.id.volverMapa)
        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        // Obtener el nombre de la actividad de origen
        val nombreJuego = intent.getStringExtra(NOMBREACTIVIDAD)
        cambiarTexto(nombreJuego, txtAcierto)

        buttonVolverMapa.setOnClickListener {
            if(nombreJuego.equals("Artopil")) {
                obtenerPiezaPatxi()
            } else {
                obtenerPieza(nombreJuego)
            }

        }
    }

    private fun cambiarTexto(nombreJuego: String?, txtAcierto: TextView) {
        when (nombreJuego) {
            "Sirena" -> {
                txtAcierto.text = getString(R.string.aciertoSirena)
            }
            "Feria" -> {
                txtAcierto.text = getString(R.string.aciertoFeria)
            }
            "Patxikotxu" -> {
                txtAcierto.text = getString(R.string.aciertoPatxikotxu)
            }
            "Artopil" -> {
                txtAcierto.text = getString(R.string.aciertoArtopil)
            }
            "Mikeldi" -> {
                txtAcierto.text = getString(R.string.encontrarMikeldi)
            }
            "Escudo" -> {
                txtAcierto.text = getString(R.string.sopa_letras_completo)
            }
        }
    }

    /**
     * Función que muestra una ventana de diálogo con un mensaje de obtención de pieza.
     *
     * @author Julio González
     */
    private fun obtenerPieza(nombreJuego: String?) {
        // Reproduce el sonido de felicitación
        val mediaPlayer = MediaPlayer.create(this, R.raw.felicitacion)
        mediaPlayer.start()

        dialog.setContentView(R.layout.ganar)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnCerrar: Button = dialog.findViewById(R.id.btnGanarCerrar)

        btnCerrar.setOnClickListener {
            if(nombreJuego == "Mikeldi"){
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "1")
                startActivity(intent)
            } else if(nombreJuego == "Feria"){
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "2")
                startActivity(intent)
            } else if(nombreJuego == "Sirena"){
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "3")
                startActivity(intent)
            } else if(nombreJuego == "Patxikotxu"){
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "5")
                startActivity(intent)
            } else if(nombreJuego == "Escudo"){
                val intent = Intent(this, MapaActivity::class.java)
                intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "6")
                startActivity(intent)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun obtenerPiezaPatxi() {
        // Reproduce el sonido de felicitación
        val mediaPlayer = MediaPlayer.create(this, R.raw.felicitacion)
        mediaPlayer.start()

        dialog.setContentView(R.layout.ganar)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnCerrar: Button = dialog.findViewById(R.id.btnGanarCerrar)

        btnCerrar.setOnClickListener {
            val intent = Intent(this, PatxikotxuActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }
}
