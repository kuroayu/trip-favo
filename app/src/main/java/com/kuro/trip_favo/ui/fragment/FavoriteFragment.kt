package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kuro.trip_favo.R
import com.kuro.trip_favo.ui.DummyData
import com.kuro.trip_favo.ui.FavoriteListAdapter

class FavoriteFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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

        val adapter = FavoriteListAdapter(dummyLists())

        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_favo)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_favo_to_favoriteSearchFragment2)
        }
        return view
    }

    private fun dummyLists(): List<DummyData> {
        return (0..20).map {
            DummyData("アパホテル", 24000, "石川県金沢市2")
        }
    }
}