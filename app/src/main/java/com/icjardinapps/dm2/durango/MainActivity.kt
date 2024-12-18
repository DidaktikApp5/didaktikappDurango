package com.icjardinapps.dm2.durango

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.icjardinapps.dm2.durango.actividades.LoginActivity
import com.icjardinapps.dm2.durango.actividades.MapaActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenSplash: SplashScreen = installSplashScreen()
        screenSplash.setKeepOnScreenCondition { false }

        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            // Si es la primera vez, abrir LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finalizamos la MainActivity para no volver a ella
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
    }
}