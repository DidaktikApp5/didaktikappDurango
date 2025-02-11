package com.icjardinapps.dm2.durango.actividades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icjardinapps.dm2.durango.R

/**
 * Adaptador para el RecyclerView que muestra las puntuaciones en el ranking.
 * Este adaptador se encarga de inflar las vistas y asignar los datos correspondientes
 * a cada elemento del RecyclerView.
 *
 * @param lista Lista de puntuaciones a mostrar en el RecyclerView.
 * @author Mikel Ramos
 */
class RankingAdapter(private val lista: List<String>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    /**
     * Clase ViewHolder que mantiene las vistas de cada ítem del RecyclerView.
     * Almacena una referencia a un TextView que se usará para mostrar la puntuación.
     *
     * @param view Vista que representa cada ítem del RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPuntuacion: TextView = view.findViewById(R.id.txtPuntuacion)
    }

    /**
     * Infla la vista del item en el RecyclerView y crea un ViewHolder para ella.
     *
     * @param parent Vista padre que contiene el RecyclerView.
     * @param viewType Tipo de vista que se necesita (no se usa en este caso).
     * @return El ViewHolder creado para un item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_puntuacion, parent, false)
        return ViewHolder(view)
    }

    /**
     * Asigna los datos de la lista a los elementos del ViewHolder.
     * En este caso, se asigna una puntuación al TextView correspondiente.
     *
     * @param holder El ViewHolder donde se va a asignar la puntuación.
     * @param position Posición del ítem en la lista de puntuaciones.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPuntuacion.text = lista[position]
    }

    /**
     * Devuelve la cantidad de ítems en la lista de puntuaciones.
     *
     * @return El tamaño de la lista de puntuaciones.
     */
    override fun getItemCount(): Int = lista.size
}
