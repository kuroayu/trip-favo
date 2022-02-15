package com.kuro.trip_favo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kuro.trip_favo.R


//ViewHolderClassをinner classや内部クラス?にするやり方もあるけど何が違うのか

class FavoriteAdapter(private val dummydata:MutableList<DummyData>): RecyclerView.Adapter<FavoriteViewHolder>() {

    //private var dataLists = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.favorite_row,parent,false)
        return FavoriteViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
     // val current = dataLists[position]
        val dummy = dummydata[position]
        holder.hotelName.text =dummy.hotelName
        holder.price.text =dummy.price.toString()
        holder.address.text =dummy.place
    }

    override fun getItemCount(): Int {
        return dummydata.size
    }

//    fun setData(data: List<User>){
//        this.dataLists = data
//        notifyDataSetChanged()
//    }

    }

class FavoriteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
  //  val image = itemView.findViewById<ImageView>(R.id.image_view)
    val hotelName = itemView.findViewById<TextView>(R.id.favo_hotel_name)
 //   val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingbar)
    val price = itemView.findViewById<TextView>(R.id.favo_price)
    val address = itemView.findViewById<TextView>(R.id.favo_address)
}