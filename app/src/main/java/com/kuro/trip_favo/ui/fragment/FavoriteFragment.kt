package com.kuro.trip_favo.ui.fragment

import android.os.Bundle
import android.util.Log
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
import com.kuro.trip_favo.ui.FavoriteAdapter

class FavoriteFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        //DataBindingにする
        val view = inflater.inflate(R.layout.activity_favofragment, container, false)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.fovo_recyclerview)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_favo)
        fab.setOnClickListener {
           findNavController().navigate(R.id.action_favo_to_favoriteSearchFragment2)
        }
//        val bottomSheet = view.findViewById<ConstraintLayout>(R.id.bottom_sheet_layout)
//
//        fab.setOnClickListener {
//            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
//        }

        val adapter = FavoriteAdapter(dummyLists())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(view.context, LinearLayoutManager.VERTICAL))
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }



    private fun dummyLists(): MutableList<DummyData> {
        var dummyLists: MutableList<DummyData> = ArrayList()
        var dummy = DummyData("アパホテル",24000,"石川県金沢市2")
        var i = 0
        while (i < 20){
            i++
            dummyLists.add(dummy)
        }

     return dummyLists
    }
}