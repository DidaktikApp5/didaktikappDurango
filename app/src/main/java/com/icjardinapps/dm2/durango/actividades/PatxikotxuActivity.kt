package com.icjardinapps.dm2.durango.actividades

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.icjardinapps.dm2.durango.R

/**
 * Clase que representa la actividad de Patxikotxu.
 * Esta clase hereda de AppCompatActivity y se encarga de mostrar la interfaz de usuario
 * La interfaz de usuario es un juego de adivinar los nombres de los personajes
 */
class PatxikotxuActivity : AppCompatActivity() {
    /**
     * Variables que se necesita
     */
    private var nombresAcertados: Int = 0

    /**
     * Variables para el audio y la comprobación
     */
    private lateinit var sonidoError: MediaPlayer
    private lateinit var sonidoCorrecto: MediaPlayer
    private var nombrePatxikotxu: String = "Patxikotxu" // Define el nombre correcto aquí
    private var nombrePantxike: String = "Pantxike" // Define el nombre correcto aquí

    /**
     * Variables para los elementos de la interfaz
     */
    private lateinit var tvPatxikotxu: TextView
    private lateinit var tvPantxike: TextView
    private lateinit var tvPotxikotxu: TextView
    private lateinit var tvPantxilotu: TextView
    private lateinit var tvPantxikixu: TextView
    private lateinit var tvPantxika: TextView
    private lateinit var tvPatxike: TextView
    private lateinit var tvPantxiko: TextView
    private lateinit var btnVolverMapa: Button
    private lateinit var btnComprobar: Button


    /**
     * Metodo para crear la interfaz
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Crea la interfaz con el layout activity_patxikotxu.xml
        setContentView(R.layout.activity_patxikotxu)

        // Inicializa los elementos de la interfaz
        tvPatxikotxu = findViewById(R.id.tvPatxikotxu)
        tvPantxike = findViewById(R.id.tvPantxike)
        tvPotxikotxu = findViewById(R.id.tvPotxikotxu)
        tvPantxilotu = findViewById(R.id.tvPantxilotu)
        tvPantxikixu = findViewById(R.id.tvPantxikixu)
        tvPantxika = findViewById(R.id.tvPantxika)
        tvPatxike = findViewById(R.id.tvPatxike)
        tvPantxiko = findViewById(R.id.tvPantxiko)
        btnVolverMapa = findViewById(R.id.btnVolverMapa)

        // Inicializa el audio de error y correcto
        // R.raw.malo es el nombre del archivo de audio de error
        sonidoError = MediaPlayer.create(this, R.raw.malo)
        // R.raw.bien es el nombre del archivo de audio de correcto
        //sonidoCorrecto = MediaPlayer.create(this, R.raw.correcto)

        // Configura el comportamiento del botón volver para volver al mapa
        btnVolverMapa.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }

        // Configura el comportamiento del TextView para el nombre.
        // En cada TextView comprueba si es el nombre correcto
        // Tambien verifica si ya se han acertado los dos nombres
        tvPatxikotxu.setOnClickListener {
            comprobarNombre(tvPatxikotxu)
            comprobarAciertos()
        }
        tvPantxike.setOnClickListener {
            comprobarNombre(tvPantxike)
            comprobarAciertos()
        }
        tvPotxikotxu.setOnClickListener {
            comprobarNombre(tvPotxikotxu)
            comprobarAciertos()
        }
        tvPantxilotu.setOnClickListener {
            comprobarNombre(tvPantxilotu)
            comprobarAciertos()
        }
        tvPantxikixu.setOnClickListener {
            comprobarNombre(tvPantxikixu)
            comprobarAciertos()
        }
        tvPantxika.setOnClickListener {
            comprobarNombre(tvPantxika)
            comprobarAciertos()
        }
        tvPatxike.setOnClickListener {
            comprobarNombre(tvPatxike)
            comprobarAciertos()
        }
        tvPantxiko.setOnClickListener {
            comprobarNombre(tvPantxiko)
            comprobarAciertos()
        }
    }

    /**
     * Metodo para comprobar si el nombre seleccionado es el correcto
     *
     */
    private fun comprobarNombre(tv : TextView) {
        // Comprueba si el nombre es correcto
        if (tv.text.toString() == nombrePatxikotxu || tv.text.toString() == nombrePantxike) {
            // Si el nombre es correcto, reproduce el audio de correcto y pinta el texto de verde
            //sonidoCorrecto.start()
            nombresAcertados++
            // R.color.verde_claro es el ID del color verde
            tv.setBackgroundColor(resources.getColor(R.color.verde_claro))
        } else {
            // Si el nombre es incorrecto, reproduce el audio de error y pinta el texto de rojo
            sonidoError.start()
            // R.color.rojo_claro es el ID del color rojo
            tv.setBackgroundColor(resources.getColor(R.color.rojo_claro))
        }
    }

    /**
     * Comprueba si los dos nombres han sido acertados
     * Si es así irá a la actividad de Resultados
     */
    private fun comprobarAciertos(){
        if(nombresAcertados == 2){
            val intent = Intent(this, ResultadosActivity::class.java)
            intent.putExtra(ResultadosActivity.nombreActividad, "Patxikotxu")
            startActivity(intent)
        }
    }

    /**
     * Para liberar los recursos de audio al destruir la activity
     */
    override fun onDestroy() {
        super.onDestroy()
        // Libera los recursos de audio al destruir la activity
        sonidoError.release()
        sonidoCorrecto.release()
    }

}