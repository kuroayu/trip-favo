package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kuro.trip_favo.R
import com.kuro.trip_favo.data.database.FavoriteApplication
import com.kuro.trip_favo.ui.FavoriteListAdapter
import com.kuro.trip_favo.ui.viewModel.FavoriteHotelViewModel
import com.kuro.trip_favo.ui.viewModel.FavoriteHotelViewModelFactory

class FavoriteFragment : Fragment() {


    private val viewModel: FavoriteHotelViewModel by viewModels {
        val application = requireActivity().application as FavoriteApplication
        FavoriteHotelViewModelFactory(
            application.favoriteHotelRepository
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        //DataBindingにする
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.fovo_recyclerview)

        val favoriteAdapter = FavoriteListAdapter()

        recyclerView.adapter = favoriteAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.allHotelData.observe(viewLifecycleOwner) { hotel ->
            favoriteAdapter.setHotel(hotel)
            favoriteAdapter.notifyDataSetChanged()
        }


        val fab = view.findViewById<FloatingActionButton>(R.id.fab_favo)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_favo_to_favoriteSearchFragment)
        }
        return view
    }

}