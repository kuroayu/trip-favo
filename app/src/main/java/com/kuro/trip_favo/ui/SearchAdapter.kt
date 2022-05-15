package com.kuro.trip_favo.ui

import HotelBasicInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuro.trip_favo.R

class SearchAdapter :
    RecyclerView.Adapter<SearchViewHolder>() {

    private var hotelBasicInfo: List<HotelBasicInfo> = emptyList()
    lateinit var listener: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.search_list_item, parent, false)
        return SearchViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val hotelData = hotelBasicInfo[position]
        holder.hotelName.text = hotelData.hotelName
        holder.price.text = hotelData.hotelMinCharge.toString() + "円〜"
        holder.address.text = hotelData.address1 + hotelData.address2
        holder.image.load(hotelData.hotelImageUrl)
        holder.ratingBar.rating = hotelData.reviewAverage.toFloat()
        holder.itemView.setOnClickListener {
            //interfaceとかを使わずにここにクリックイベント処理かけないのはなんで
            //あ、書けないわけではなくてdataBindするとこに書くことでもないってことか？
            listener.onItemClick(it, position, hotelData)
        }
        holder.favoriteButton.setOnClickListener {
            listener.onItemClick(it, position, hotelData)
        }
    }

    //Adapterのinterfaceをoverride的なのかと思いきや自作っぽい
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, data: HotelBasicInfo)
    }

    //これfragmentに書くじゃダメなの
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    override fun getItemCount(): Int {
        return hotelBasicInfo.size
    }

    fun setHotelInfo(hotelBasicInfo: List<HotelBasicInfo>) {
        this.hotelBasicInfo = hotelBasicInfo
    }
}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val hotelName: TextView = itemView.findViewById(R.id.search_hotel_name)
    val price: TextView = itemView.findViewById(R.id.search_price)
    val address: TextView = itemView.findViewById(R.id.search_address)
    val ratingBar: RatingBar = itemView.findViewById(R.id.search_ratingbar)
    val favoriteButton: ImageView = itemView.findViewById(R.id.favorite_button)

    //var出ないとbind出来ない
    var image: ImageView = itemView.findViewById(R.id.search_image)

}



