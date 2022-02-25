package com.kuro.trip_favo.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuro.trip_favo.R
import com.kuro.trip_favo.ui.DummyData
import com.kuro.trip_favo.ui.FavoriteListAdapter
import com.kuro.trip_favo.ui.SearchAdapter
import com.kuro.trip_favo.ui.SearchDummyData

class SearchResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_search_result, container, false)
        val linearLayoutManager = LinearLayoutManager(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recyclerview)

        val adapter = SearchAdapter(searchDummyLists())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )
        return view
    }

    private fun searchDummyLists(): List<SearchDummyData> {
        return (0..20).map {
            SearchDummyData("東横イン", 35000, "東京都立川市1-2-3")
        }
    }
}
