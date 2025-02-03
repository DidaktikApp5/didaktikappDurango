package com.icjardinapps.dm2.durango.actividades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icjardinapps.dm2.durango.R

class RankingAdapter(private val lista: List<String>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtPuntuacion: TextView = view.findViewById(R.id.txtPuntuacion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_puntuacion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPuntuacion.text = lista[position]
    }

    override fun getItemCount(): Int = lista.size
}
