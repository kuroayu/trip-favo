package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuro.trip_favo.R
import com.kuro.trip_favo.databinding.FragmentFavoriteSearchBinding
import com.kuro.trip_favo.ui.viewModel.FavoriteHotelViewModel


class FavoriteSearchFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentFavoriteSearchBinding
    private val viewModel: FavoriteHotelViewModel by activityViewModels()


    private val orderAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.order)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavoriteSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.favoriteOrderSpinner.adapter = orderAdapter

        binding.favoriteSearchButton.setOnClickListener {

            viewModel.selectedOrder()
            findNavController().popBackStack()

        }

    }

}
