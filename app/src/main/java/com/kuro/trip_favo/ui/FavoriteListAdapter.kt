package com.kuro.trip_favo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuro.trip_favo.R
import com.kuro.trip_favo.data.database.FavoriteHotel


class FavoriteListAdapter() :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    private var favoriteHotel: List<FavoriteHotel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.favorite_list_item, parent, false)
        return FavoriteViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteHotelList = favoriteHotel[position]
        holder.hotelName.text = favoriteHotelList.hotelName
        holder.price.text = favoriteHotelList.hotelMinCharge.toString() + "円〜"
        holder.address.text = favoriteHotelList.address1 + favoriteHotelList.address2
        holder.ratingBar.rating = favoriteHotelList.reviewAverage.toFloat()
        holder.image.load(favoriteHotelList.imageUrl)
    }

    override fun getItemCount(): Int {
        return favoriteHotel.size
    }

    fun setHotel(favoriteHotel: List<FavoriteHotel>) {
        this.favoriteHotel = favoriteHotel
    }
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hotelName: TextView = itemView.findViewById(R.id.favorite_hotel_name)
    val price: TextView = itemView.findViewById(R.id.favorite_price)
    val address: TextView = itemView.findViewById(R.id.favorite_address)
    val ratingBar: RatingBar = itemView.findViewById(R.id.favorite_ratingbar)
    var image: ImageView = itemView.findViewById(R.id.favorite_image)
}