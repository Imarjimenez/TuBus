package com.imarjimenez.tubus.ui.paradero1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.GeoPoint
import com.imarjimenez.tubus.R
import com.imarjimenez.tubus.databinding.FragmentParaderoRuta1Binding
import com.imarjimenez.tubus.ui.model.Paradero

class ListParadero1Fragment : Fragment() {

    private lateinit var listParaderosBinding: FragmentParaderoRuta1Binding
    private val paraderosList = mutableListOf<Paradero>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listParaderosBinding = FragmentParaderoRuta1Binding.inflate(inflater, container, false)
        val root : View = listParaderosBinding.root

        loadParaderos()

        val paraderosRecyclerViewAdapter = ParaderosRecyclerViewAdapter(paraderosList, onItemClicked = { onParaderosItemClicked(it)})
        listParaderosBinding.paraderosRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListParadero1Fragment.requireContext())
            adapter = paraderosRecyclerViewAdapter
            setHasFixedSize(false)

        }
        return root

    }

    private fun loadParaderos() {
        paraderosList.clear()
        var rutas = Paradero(uid = "0",
            location = GeoPoint(6.276285 , 75.58679),
            name=resources.getString(R.string.first_paradero),
            image= R.drawable.alfiler)
        paraderosList.add(rutas)

        rutas = Paradero(uid = "1",
            location = GeoPoint(6.260911, 75.579902),
            name=resources.getString(R.string.second_paradero),
            image = R.drawable.alfiler)

        paraderosList.add(rutas)

        rutas = Paradero(uid = "2",
            location = GeoPoint(6.264574, 75.575236),
            name=resources.getString(R.string.third_paradero),
            image = R.drawable.alfiler)

        paraderosList.add(rutas)

        rutas = Paradero(uid = "3",
            location = GeoPoint(6.248372, 75.562427),
            name=resources.getString(R.string.fourth_paradero),
            image = R.drawable.alfiler)

        paraderosList.add(rutas)

        rutas = Paradero(uid = "4",
            location = GeoPoint(6.256995, 75.566141),
            name=resources.getString(R.string.fifth_paradero),
            image = R.drawable.alfiler)

        paraderosList.add(rutas)



    }

    private fun onParaderosItemClicked(paradero: Paradero) {
        //por fragmento
        when (paradero.uid) {
            "0" -> findNavController().navigate(R.id.action_listBusFragment_to_first_paradero)
            "1" -> findNavController().navigate(R.id.action_listBusFragment_to_second_paradero)
            else -> findNavController().navigate(R.id.action_listBusFragment_to_third_paradero)
        }

    }
}
