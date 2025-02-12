package com.icjardinapps.dm2.durango.actividades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icjardinapps.dm2.durango.R

/**
 * Adaptador para mostrar una lista de puntuaciones en un RecyclerView.
 *
 * @param lista Lista de puntuaciones representadas como Strings.
 * @version 1.0
 * @author DidaktikAppDurango
 */
class RankingAdapter(private val lista: List<String>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    /**
     * Constructor del ViewHolder.
     *
     * @param view Vista del elemento de la lista.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPuntuacion: TextView = view.findViewById(R.id.txtPuntuacion)
    }

    /**
     * Crea nuevas vistas (invocado por el layout manager).
     *
     * @param parent   El ViewGroup al que se agregará la nueva vista.
     * @param viewType El tipo de vista de la nueva vista.
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_puntuacion, parent, false)
        return ViewHolder(view)
    }

    /**
     * Reemplaza el contenido de una vista (invocado por el layout manager).
     *
     * @param holder   El ViewHolder que debe ser actualizado.
     * @param position La posición del elemento dentro de la lista de datos.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPuntuacion.text = lista[position]
    }

    /**
     * Devuelve el tamaño del conjunto de datos (invocado por el layout manager).
     *
     * @return El número total de elementos en la lista.
     */
    override fun getItemCount(): Int = lista.size
}
