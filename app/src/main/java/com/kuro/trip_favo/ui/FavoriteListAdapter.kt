package com.kuro.trip_favo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuro.trip_favo.R


class FavoriteListAdapter(private val dummydata: List<DummyData>) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.favorite_list_item, parent, false)
        return FavoriteViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val dummy = dummydata[position]
        holder.hotelName.text = dummy.hotelName
        holder.price.text = dummy.price.toString()
        holder.address.text = dummy.place
    }

    override fun getItemCount(): Int {
        return dummydata.size
    }
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hotelName: TextView = itemView.findViewById(R.id.favo_hotel_name)
    val price: TextView = itemView.findViewById(R.id.favo_price)
    val address: TextView = itemView.findViewById(R.id.favo_address)
}