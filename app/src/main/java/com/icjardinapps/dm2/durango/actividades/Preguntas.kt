package com.icjardinapps.dm2.durango.actividades

import android.content.Context
import com.icjardinapps.dm2.durango.R

/**
 * Clase para guardar la información de las preguntas y su respuesta correcta
 * en la actividad de verdadero o falso de la basílica.
 *
 * @author Julio González
 */
object Preguntas {
    /**
     * Array para almacenar las preguntas
     */
    lateinit var preguntas: Array<String>

    /**
     * Imagenes a mostrar de cada pregunta
     */
    val imagenes = intArrayOf(
        R.drawable.basilica, R.drawable.xv, R.drawable.bombardeo, R.drawable.portico
    )

    /**
     * Orden de respuestas asociadas a cada pregunta
     */
    val respuestas = booleanArrayOf(
        true, false, true, false
    )

    /**
     * Inicializar las preguntas con un contexto
     * con su texto asociado
     *
     * @author Julio González
     * @param context Contexto
     */
    fun init(context: Context) {
        preguntas = arrayOf(
            context.getString(R.string.pregunta_1),
            context.getString(R.string.pregunta_2),
            context.getString(R.string.pregunta_3),
            context.getString(R.string.pregunta_4)
        )
    }
}