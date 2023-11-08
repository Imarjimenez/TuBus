package com.imarjimenez.tubus.ui.paradero1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imarjimenez.tubus.R
import com.imarjimenez.tubus.databinding.ItemParaderoBinding
import com.imarjimenez.tubus.ui.model.Paradero

class ParaderosRecyclerViewAdapter(
    private val paraderoList: MutableList<Paradero>,
    private val onItemClicked: (Paradero) -> Unit

) : RecyclerView.Adapter<ParaderosRecyclerViewAdapter.ParaderoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParaderoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_paradero, parent,false)
        return ParaderoViewHolder(itemView)
    }

    override fun getItemCount(): Int = paraderoList.size


    override fun onBindViewHolder(holder: ParaderoViewHolder, position: Int) {
        val paradero = paraderoList[position]
        holder.bindSuperheroe(paradero)
        holder.itemView.setOnClickListener{ onItemClicked(paradero)}
    }
    class ParaderoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = ItemParaderoBinding.bind(itemView)

        fun bindSuperheroe(paradero: Paradero){
            with(binding){
                nameTextView.text = paradero.name
                paraderoImageView.setImageResource(paradero.image)

            }
        }

    }
}