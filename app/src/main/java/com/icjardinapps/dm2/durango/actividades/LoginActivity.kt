package com.icjardinapps.dm2.durango.actividades

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.icjardinapps.dm2.durango.MainActivity
import com.icjardinapps.dm2.durango.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val botonComenzar: Button = findViewById(R.id.btnComenzar)
        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        botonComenzar.setOnClickListener {
            val nombre = editTextNombre.text.toString()

            if (nombre.isNotBlank()) {
                // Guardamos el nombre de usuario en SharedPreferences
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", nombre)
                editor.putBoolean("isFirstTime", false) // Marcar que ya no es la primera vez
                editor.apply()

                // Iniciar la actividad principal
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Mostrar un mensaje si el nombre está vacío
                editTextNombre.error = "Por favor ingrese un nombre"
            }
        }
    }
}