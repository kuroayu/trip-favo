package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuro.trip_favo.R

class FavoriteSearchFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_favorite_search_fragment, container, false)

        val favoSpinner = view.findViewById<Spinner>(R.id.favo_order_spinner)
        val item = resources.getStringArray(R.array.order)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, item)
        favoSpinner.adapter = adapter
        return view
    }
}