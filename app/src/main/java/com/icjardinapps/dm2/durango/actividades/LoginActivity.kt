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
import com.icjardinapps.dm2.durango.db.ConexionDb

/**
 * Clase que representa la actividad de inicio de sesión.
 * Aparece la primera vez que se inicia la aplicación para introducir tu nombre.
 *
 * @author Julio González
 */
class LoginActivity : AppCompatActivity() {
    /**
     * Al iniciar la actividad.
     * Controla la animación de la mascota y guardar el nombre introducido.
     *
     * @author Julio González
     * @param savedInstanceState Estado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        val bd=ConexionDb(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Asociar con el xml
        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val botonComenzar: Button = findViewById(R.id.btnComenzar)
        val mascotaImage = findViewById<ImageView>(R.id.iv_Mascota)

        // Inicia la animación cuando se carga la ventana
        mascotaImage.setImageResource(R.drawable.idle)
        (mascotaImage.drawable as AnimationDrawable).start()

        botonComenzar.setOnClickListener {
            var nombre = editTextNombre.text.toString()

            if (nombre.isNotBlank()) {
                // Guardamos el nombre de usuario en SharedPreferences
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", nombre)

                Thread {
                    var insertSuccess = bd.guardarAlumnoBBDD(nombre)

                    if(!insertSuccess) {
                        var num = 1
                        do {
                            insertSuccess = bd.guardarAlumnoBBDD(nombre + num)
                            num++
                        } while(!insertSuccess)
                        nombre+=(num-1)
                    }
                }.start()

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