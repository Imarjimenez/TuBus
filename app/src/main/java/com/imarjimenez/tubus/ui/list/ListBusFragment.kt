package com.imarjimenez.tubus.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.imarjimenez.tubus.R
import com.imarjimenez.tubus.databinding.FragmentListBusBinding
import com.imarjimenez.tubus.ui.model.Rutas


class ListBusFragment : Fragment() {

    private lateinit var listBusBinding: FragmentListBusBinding
    private val rutasList = mutableListOf<Rutas>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listBusBinding = FragmentListBusBinding.inflate(inflater, container, false)
        val root : View = listBusBinding.root

        loadRutas()

        val rutasRecyclerViewAdapter = ListBusRecyclerViewAdapter(rutasList, onItemClicked = { onRutasItemClicked(it)})
        listBusBinding.rutasRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListBusFragment.requireContext())
            adapter = rutasRecyclerViewAdapter
            setHasFixedSize(false)

        }
        return root

    }

    private fun loadRutas() {
        rutasList.clear()
        var rutas = Rutas(uid = "0",
            name=resources.getString(R.string.first_Bus),
            image= R.drawable.autobus)
        rutasList.add(rutas)

        rutas = Rutas(uid = "1",
            name=resources.getString(R.string.second_Bus),
            image = R.drawable.autobus)

        rutasList.add(rutas)

        rutas = Rutas(uid = "2",
            name=resources.getString(R.string.third_Bus),
            image = R.drawable.autobus)

        rutasList.add(rutas)



    }

    private fun onRutasItemClicked(rutas: Rutas) {
        //con un fragmento detalle
        when (rutas.uid) {
            "0" -> findNavController().navigate(ListBusFragmentDirections.actionListBusFragmentToFirstParadero())
            "1" -> findNavController().navigate(ListBusFragmentDirections.actionListBusFragmentToSecondParadero())
            else -> findNavController().navigate(ListBusFragmentDirections.actionListBusFragmentToThirdParadero())
        }

    }
}