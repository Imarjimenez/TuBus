package com.imarjimenez.tubus.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imarjimenez.tubus.R
import androidx.recyclerview.widget.RecyclerView
import com.imarjimenez.tubus.databinding.ItemRutaBinding
import com.imarjimenez.tubus.ui.model.Rutas

class ListBusRecyclerViewAdapter(
    private val ListBus: MutableList<Rutas>,
    private val onItemClicked: (Rutas) -> Unit
) : RecyclerView.Adapter<ListBusRecyclerViewAdapter.RutasViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutasViewHolder {
        // Infla el diseño correcto (item_ruta.xml) usando ItemRutaBinding
        val binding = ItemRutaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RutasViewHolder(binding)
    }

    override fun getItemCount(): Int = ListBus.size

    override fun onBindViewHolder(holder: RutasViewHolder, position: Int) {
        val driver = ListBus[position]
        holder.bindRutas(driver)
        holder.itemView.setOnClickListener{ onItemClicked(driver)}
    }

    class RutasViewHolder(private val binding: ItemRutaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindRutas(Rutas: Rutas) {
            with(binding) {
                nameTextView.text = Rutas.name
                rutasImageView.setImageResource(Rutas.image)
            }
        }
    }
}