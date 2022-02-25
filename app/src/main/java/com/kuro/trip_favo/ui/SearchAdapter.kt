package com.kuro.trip_favo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuro.trip_favo.R

class SearchAdapter(private val searchDummyData: List<SearchDummyData>): RecyclerView.Adapter<SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.search_list_item, parent, false)
        return SearchViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val dummy = searchDummyData[position]
        holder.hotelName.text = dummy.searchHotelName
        holder.price.text = dummy.searchPrice.toString()
        holder.address.text = dummy.searchPlace
    }

    override fun getItemCount(): Int {
        return searchDummyData.size
    }
}

class SearchViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val hotelName: TextView = itemView.findViewById(R.id.search_hotel_name)
    val price: TextView = itemView.findViewById(R.id.search_price)
    val address: TextView = itemView.findViewById(R.id.search_address)
}

