package com.imarjimenez.tubus.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.imarjimenez.tubus.R
import com.imarjimenez.tubus.databinding.FragmentDetailBinding
import com.imarjimenez.tubus.ui.model.Paradero
import com.imarjimenez.tubus.ui.paradero1.ParaderosRecyclerViewAdapter
class DetailFragment : Fragment() {

    private lateinit var detailBinding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        detailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = detailBinding.root

        val rutas = args.rutas

        with(detailBinding) {
            // Muestra el nombre del paradero en un TextView
            //nameTextView.text = rutas.name

            // Configura el RecyclerView
            val paraderosRecyclerView = paraderosRecyclerView // Asegúrate de que esta sea la ID correcta
            val paraderosList = mutableListOf<Paradero>() // Define una lista de paraderos y agrega datos
            val paraderosAdapter = ParaderosRecyclerViewAdapter(paraderosList) {
                // Manejar clics en los paraderos si es necesario
            }
            paraderosRecyclerView.adapter = paraderosAdapter

            // Otras configuraciones del RecyclerView (como el diseño y el administrador de diseño) se pueden realizar aquí
        }

        return root
    }
}