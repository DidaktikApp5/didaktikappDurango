package com.icjardinapps.dm2.durango

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icjardinapps.dm2.durango.actividades.LoginActivity
import com.icjardinapps.dm2.durango.actividades.MapaActivity
import java.util.Locale

/**
 * Actividad principal que muestra el menú de inicio de la aplicación
 *
 * @author Julio González
 */
class MainActivity : AppCompatActivity() {
    /**
     * Ventana de diálogo para mostrar el acerca de
     */
    private lateinit var dialog: Dialog

    /**
     * Función que se llama cuando se inicia la actividad.
     * Maneja la verificación de si es la primera vez que se inicia la aplicación
     * y el cambio de idioma.
     *
     * @author Julio González
     * @param savedInstanceState Estado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el diálogo
        dialog = Dialog(this)

        // Iniciar el Splash Screen
        val screenSplash: SplashScreen = installSplashScreen()
        screenSplash.setKeepOnScreenCondition { false }

        // Verificar si es la primera vez
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            // Si es la primera vez, abrir LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la MainActivity para no volver a ella
        } else {
            // Si ya no es la primera vez, mostrar la actividad principal
            setContentView(R.layout.activity_main)
        }

        // Mostrar el nombre del usuario introducido en el LoginActivity
        val textViewBienvenida: TextView = findViewById(R.id.tv_nombreJugador)
        val user:String = sharedPreferences.getString("username", "").toString()

        textViewBienvenida.text = getString(R.string.bienvenida, user)

        // Ir a MapaActivity
        val cardViewJugar: CardView = findViewById(R.id.card_jugar)
        cardViewJugar.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }

        // Cambiar idioma
        val cardViewIdioma: CardView = findViewById(R.id.card_lenguaje)
        cardViewIdioma.setOnClickListener {
            val currentLanguage = sharedPreferences.getString("idioma", "es") // Obtener el idioma actual
            val newLanguage = if (currentLanguage == "es") "eu" else "es" // Cambiar el idioma a euskera o español
            sharedPreferences.edit().putString("idioma", newLanguage).apply() // Guardar el nuevo idioma

            // Cambiar el idioma de la aplicación
            val locale = Locale(newLanguage)
            Locale.setDefault(locale)
            val config = resources.configuration
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)

            // Reiniciar la actividad para aplicar los cambios de idioma
            recreate()
        }

        // Mostrar Acerca de
        val cardViewAcercaDe: CardView = findViewById(R.id.card_acercaDe)
        cardViewAcercaDe.setOnClickListener {
            acercaDe()
        }
    }

    /**
     * Función que muestra una ventana de diálogo con los desarrolladores de la app
     *
     * @author Julio González
     */
    private fun acercaDe() {
        dialog.setContentView(R.layout.acerca_de)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val ivCerrar: ImageView = dialog.findViewById(R.id.ivAcercaDeCerrar)

        val btnCerrar: Button = dialog.findViewById(R.id.btnAcercaDeCerrar)

        ivCerrar.setOnClickListener {
            dialog.dismiss()
        }

        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}