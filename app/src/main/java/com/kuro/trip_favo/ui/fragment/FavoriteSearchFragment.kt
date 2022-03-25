package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuro.trip_favo.R


class FavoriteSearchFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val view = inflater.inflate(R.layout.favorite_search_fragment, container, false)

        val favoriteSpinner = view.findViewById<Spinner>(R.id.favorite_order_spinner)
        val item = resources.getStringArray(R.array.order)
        val currentOrder = view.findViewById<TextView>(R.id.current_order)

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, item)
        favoriteSpinner.adapter = adapter
        favoriteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val order = parent.getItemAtPosition(position)
                currentOrder.text = order.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        return view
    }
}