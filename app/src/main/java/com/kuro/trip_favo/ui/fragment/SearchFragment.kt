package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuro.trip_favo.R
import com.kuro.trip_favo.databinding.FragmentSearchBinding
import com.kuro.trip_favo.ui.viewModel.SearchViewModel


class SearchFragment : Fragment(R.layout.fragment_search) {


    private var selectedRating: Int? = -1
    private var squeezeCondition: String? = ""

    private val middleAreaAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<String>()
        )
    }

    private val smallAreaAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<String>()
        )
    }

    private val detailAreaAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf<String>()
        )
    }

    private val viewModel: SearchViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.middleSpinner.adapter = middleAreaAdapter
        binding.smallSpinner.adapter = smallAreaAdapter
        binding.detailSpinner.adapter = detailAreaAdapter



        viewModel.middleAreaNameList.observe(viewLifecycleOwner) {
            middleAreaAdapter.clear()
            middleAreaAdapter.addAll(it)
            middleAreaAdapter.notifyDataSetChanged()
            Log.d("smallName", it.toString())
        }
        viewModel.smallAreaNameList.observe(viewLifecycleOwner) {
            smallAreaAdapter.clear()
            smallAreaAdapter.addAll(it)
            smallAreaAdapter.notifyDataSetChanged()
            Log.d("smallName", it.toString())
        }

        viewModel.detailAreaNameList.observe(viewLifecycleOwner) {
            detailAreaAdapter.clear()
            detailAreaAdapter.addAll(it)
            detailAreaAdapter.notifyDataSetChanged()
            Log.d("detailName", it.toString())
        }

        binding.ratingbar.setOnRatingBarChangeListener { _, rating, _ ->
            selectedRating = rating.toInt()
        }

        binding.searchOnsenSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("selectedSwitch", "onsen")
                squeezeCondition = "onsen"

            } else if (!isChecked) {
                Log.d("selectedSwitch", "null")
                squeezeCondition = ""

            }
        }

        binding.searchButton.setOnClickListener {
            val middleClassCode = viewModel.getMiddleAreaCode() ?: return@setOnClickListener
            val smallClassCode = viewModel.getSmallAreaCode() ?: return@setOnClickListener
            val detailClassCode = viewModel.getDetailAreaCode() ?: ""

            findNavController().navigate(
                SearchFragmentDirections.actionSearchToSearchResultFragment(
                    middleClassCode,
                    smallClassCode,
                    detailClassCode,
                    selectedRating!!,
                    squeezeCondition!!
                )
            )

        }

    }
}
