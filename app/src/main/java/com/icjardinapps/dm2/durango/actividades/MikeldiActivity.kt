package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

/**
 * Actividad que representa la pantalla de Mikeldi.
 *
 * En esta actividad, el usuario puede interactuar con la imagen de Mikeldi
 * y navegar a la pantalla de resultados o volver al mapa principal.
 */
class MikeldiActivity : AppCompatActivity() {

    private lateinit var ivMikeldi: ImageView
    private lateinit var btnVolverMapa: Button

    /**
     * Metodo llamado al crear la actividad.
     *
     * Se establecen los elementos de la interfaz y los eventos de clic en los botones.
     *
     * @param savedInstanceState Estado de la actividad en caso de recreación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mikeldi)

        ivMikeldi = findViewById(R.id.ivMikeldi)
        btnVolverMapa = findViewById(R.id.btnVolverMapa)

        /**
         * Evento de clic en la imagen de Mikeldi.
         *
         * Redirige al usuario a la pantalla de resultados con la actividad "Mikeldi".
         */
        ivMikeldi.setOnClickListener {
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra(ResultadosActivity.NOMBREACTIVIDAD, "Mikeldi")
            startActivity(intent)
        }

        /**
         * Evento de clic en el botón "Volver al mapa".
         *
         * Redirige al usuario a la pantalla del mapa y envía el número de actividad "0".
         */
        btnVolverMapa.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            intent.putExtra(MapaActivity.NUMEROACTIVIDAD, "0")
            startActivity(intent)
        }
    }
}
