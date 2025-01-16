package com.icjardinapps.dm2.durango.actividades

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.R

class DespedidaActivity : AppCompatActivity() {
    private lateinit var btnSalir: Button

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