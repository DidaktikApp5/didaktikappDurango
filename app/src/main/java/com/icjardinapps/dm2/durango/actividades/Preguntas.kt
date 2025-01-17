package com.icjardinapps.dm2.durango.actividades

import android.content.Context
import com.icjardinapps.dm2.durango.R

object Preguntas {
    lateinit var preguntas: Array<String>

    val imagenes = intArrayOf(
        R.drawable.basilica, R.drawable.xv, R.drawable.bombardeo, R.drawable.portico
    )
    val respuestas = booleanArrayOf(
        true, false, true, false
    )

    // Inicializar las preguntas con un contexto
    fun init(context: Context) {
        preguntas = arrayOf(
            context.getString(R.string.pregunta_1),
            context.getString(R.string.pregunta_2),
            context.getString(R.string.pregunta_3),
            context.getString(R.string.pregunta_4)
        )
    }
}