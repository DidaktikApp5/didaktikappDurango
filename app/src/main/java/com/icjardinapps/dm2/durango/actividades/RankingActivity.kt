package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.icjardinapps.dm2.durango.MainActivity
import com.icjardinapps.dm2.durango.R
import com.icjardinapps.dm2.durango.databinding.ActivityMapaBinding
import com.icjardinapps.dm2.durango.databinding.ActivityRankingBinding
import com.icjardinapps.dm2.durango.db.ConexionDb

class RankingActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RankingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val bd = ConexionDb(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        recyclerView = findViewById(R.id.recyclerViewRanking)
        val btnVolver: Button = findViewById(R.id.volver)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Iniciar el adapter vacío (es necesario antes de usarlo en el hilo principal)
        adapter = RankingAdapter(emptyList())

        // Asignar el Adapter con los datos en el hilo principal
        recyclerView.adapter = adapter

        // Obtener los datos de la base de datos en un hilo secundario
        Thread {
            val puntuaciones = bd.todasPuntuaciones()

            // Actualizar la UI en el hilo principal
            runOnUiThread {
                adapter = RankingAdapter(puntuaciones) // Crear el nuevo adapter con los datos
                recyclerView.adapter = adapter // Asignar el adapter actualizado
            }
        }.start()

        btnVolver.setOnClickListener {
            abrirActividad(MainActivity::class.java)
        }

    }

    // Función para abrir actividades
    private fun <T> abrirActividad(clase: Class<T>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}

