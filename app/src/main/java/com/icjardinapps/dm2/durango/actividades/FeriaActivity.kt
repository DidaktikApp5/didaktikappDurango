package com.icjardinapps.dm2.durango.actividades

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

class FeriaActivity : AppCompatActivity() {
    private lateinit var bolsa: ImageView
    private lateinit var btnVolver: Button
    private lateinit var btnComprobar: Button
    private lateinit var items: List<ImageView>
    private val objects = mutableListOf<Int>() // Lista de objetos a colocar en la bolsa
    private val positions = mutableListOf<Int>() // Lista de posiciones correctas de los objetos en la bolsa
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feria)

        // Inicialización de las views
        bolsa = findViewById(R.id.bolsa)
        btnVolver = findViewById(R.id.btnVolver)
        btnComprobar = findViewById(R.id.btnComprobar)
        items = listOf(
            findViewById(R.id.item1),
            findViewById(R.id.item2),
            findViewById(R.id.item3),
            findViewById(R.id.item4),
            findViewById(R.id.item5),
            findViewById(R.id.item6)
        )

        // Inicializar la lista de objetos y sus posiciones
        initializeObjects()

        // Inicializar el reproductor de audio
        mediaPlayer = MediaPlayer.create(this, R.raw.error_sound) // Reemplaza R.raw.error_sound con tu archivo de sonido de error

        // Establecer el comportamiento de cada view
        btnVolver.setOnClickListener {
            finish() // Cierra la actividad actual
        }

        btnComprobar.setOnClickListener {
            checkObjects() // Verifica si los objetos están en la posición correcta
        }

        // Establecer el comportamiento del arrastrar y soltar
        items.forEach { item ->
            item.setOnTouchListener { view, motionEvent ->
                // Implementar lógica de arrastrar y soltar (se requiere una biblioteca externa)
                // Puedes utilizar la biblioteca Drag and Drop de Android para implementar esta funcionalidad
                true
            }
        }
    }

    private fun initializeObjects() {
        // Aquí puedes agregar la lógica para inicializar los objetos y sus posiciones
        // Por ejemplo, agregar imágenes a la lista de objetos y definir sus posiciones
    }

    private fun checkObjects() {
        // Lógica para comprobar si los objetos están en las posiciones correctas
        // Si están correctos, muestra un mensaje de éxito, de lo contrario, reproduce un sonido de error
        Toast.makeText(this, "Comprobando objetos...", Toast.LENGTH_SHORT).show()
        // Aquí puedes agregar la lógica para verificar las posiciones
    }

}