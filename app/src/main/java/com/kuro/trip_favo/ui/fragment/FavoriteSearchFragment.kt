package com.kuro.trip_favo.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.kuro.trip_favo.R

class FavoriteSearchFragment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_search_fragment)


        val currentOrder = findViewById<TextView>(R.id.current_order)
        val favoSpinner = findViewById<Spinner>(R.id.favo_order_spinner)
        val item = resources.getStringArray(R.array.order)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,item)
        favoSpinner.adapter = adapter
        favoSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
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
    }


}