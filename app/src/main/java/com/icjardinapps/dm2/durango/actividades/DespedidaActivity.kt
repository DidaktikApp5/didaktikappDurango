package com.icjardinapps.dm2.durango.actividades

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

/**
 * Clase para mostrar la ventana de despedida de la aplicación.
 *
 * @author Julio González
 */
class DespedidaActivity : AppCompatActivity() {
    /**
     * Botón par salir de la aplicación.
     */
    private lateinit var btnSalir: Button

    /**
     * Al iniciar la actividad. Controla la animación de la mascota y el botón para salir.
     *
     * @author Julio González
     * @param savedInstanceState Estado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_despedida)

        val mascotaImage = findViewById<ImageView>(R.id.ivMascotaDespedida)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        btnSalir = findViewById(R.id.btnSalirDespedida)

        btnSalir.setOnClickListener {
            finish()
        }
    }
}