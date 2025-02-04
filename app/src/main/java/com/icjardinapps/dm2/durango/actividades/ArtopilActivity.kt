package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class ArtopilActivity : AppCompatActivity() {
    /**
     * Variables para los elementos de la interfaz
     */
    private lateinit var btnVolverMapa: Button
    private lateinit var ivArtopil: ImageView
    private lateinit var ivMadalena: ImageView
    private lateinit var ivRoscon: ImageView
    private lateinit var ivBizcocho: ImageView
    private lateinit var tvVidas: TextView

    private lateinit var ivCorrecto: ImageView
    private lateinit var sonidoError: MediaPlayer

    private var vidasRestantes: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artopil)
        btnVolverMapa = findViewById(R.id.btnVolverMapa)
        ivArtopil = findViewById(R.id.ivArtopil)
        ivMadalena = findViewById(R.id.ivMadalena)
        ivRoscon = findViewById(R.id.ivRoscon)
        ivBizcocho = findViewById(R.id.ivBizcocho)
        tvVidas = findViewById(R.id.tvVidas)
        ivCorrecto = ivArtopil
        sonidoError = MediaPlayer.create(this, R.raw.malo)
        btnVolverMapa.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "4")
            startActivity(intent)
        }
        ivArtopil.setOnClickListener{
            comprobarImagen(ivArtopil)
        }
        ivMadalena.setOnClickListener{
            comprobarImagen(ivMadalena)
        }
        ivRoscon.setOnClickListener{
            comprobarImagen(ivRoscon)
        }
        ivBizcocho.setOnClickListener{
            comprobarImagen(ivBizcocho)
        }

    }

    private fun comprobarImagen(iv: ImageView) {
        if(iv == ivCorrecto){
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra(ResultadosActivity.NOMBREACTIVIDAD, "Artopil")
            startActivity(intent)
        } else {
            vidasRestantes--
            Toast.makeText(this, getString(R.string.errorArtopil), Toast.LENGTH_SHORT).show()
            sonidoError.start()
            actualizarVidas()
            iv.setImageDrawable(null)
        }
    }

    private fun actualizarVidas() {
        tvVidas.text = "$vidasRestantes"
    }
}