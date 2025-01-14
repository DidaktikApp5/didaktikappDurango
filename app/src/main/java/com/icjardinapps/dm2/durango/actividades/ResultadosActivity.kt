package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class ResultadosActivity : AppCompatActivity() {

    companion object {
        const val nombreActividad = "nombreJuego"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        val txtAcierto: TextView = findViewById(R.id.txtAcierto)
        val buttonVolverMapa: Button = findViewById(R.id.volverMapa)
        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        // Obtener el nombre de la actividad de origen
        val nombreJuego = intent.getStringExtra(nombreActividad)
        cambiarTexto(nombreJuego, txtAcierto)

        buttonVolverMapa.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
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
        }
    }
}
