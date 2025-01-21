package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class MikeldiActivity : AppCompatActivity() {
    private lateinit var ivMikeldi: ImageView
    private lateinit var btnVolverMapa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mikeldi)
        ivMikeldi = findViewById(R.id.ivMikeldi)
        btnVolverMapa = findViewById(R.id.btnVolverMapa)
        ivMikeldi.setOnClickListener{
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra(ResultadosActivity.nombreActividad, "Mikeldi")
            startActivity(intent)
        }
        btnVolverMapa.setOnClickListener{
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }
    }
}