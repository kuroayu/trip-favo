package com.kuro.trip_favo.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuro.trip_favo.R
import com.kuro.trip_favo.data.api.HotelBasicInfo
import com.kuro.trip_favo.databinding.SearchListItemBinding
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModel

class SearchAdapter :
    RecyclerView.Adapter<SearchViewHolder>() {

    private var hotelBasicInfo: List<SearchResultViewModel.RenderListItem> = emptyList()
    lateinit var listener: OnItemClickListener
//    lateinit var buttonListener: OnButtonClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = SearchListItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val hotelData = hotelBasicInfo[position]
        holder.binding.item = hotelData
        holder.image.load(hotelData.hotelBasicInfo.hotelImageUrl)

        holder.favoriteButton.setOnClickListener {
            listener.onFavoriteButtonClick(it, hotelData.hotelBasicInfo)
            Log.d("favoriteButton", "favoriteButton")
        }
        if (hotelData.isRegistered) {
            holder.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position, hotelData.hotelBasicInfo)
        }
    }


//        if (hotelData.isRegistered) {
//            holder.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//            holder.favoriteButton.setOnClickListener {
//                listener.onItemDeleteClick(hotelData.hotelBasicInfo)
//            }
//        } else if (!hotelData.isRegistered) {
//            holder.favoriteButton.setOnClickListener {
//                listener.onItemInsertClick(hotelData.hotelBasicInfo)
//            }
//        }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, data: HotelBasicInfo)
        fun onFavoriteButtonClick(favoriteButton: View, data: HotelBasicInfo)
    }

//        //        //追記
//        fun onItemDeleteClick(data: HotelBasicInfo)
//        fun onItemInsertClick(data: HotelBasicInfo)


//    interface OnButtonClickListener {
//        fun onItemDeleteClick(data: HotelBasicInfo)
//        fun onItemInsertClick(data: HotelBasicInfo)
//    }

//    fun setOnButtonClickListener(listener: OnButtonClickListener){
//        .buttonListener = listener
//    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return hotelBasicInfo.size
    }

    fun setHotelInfo(hotelBasicInfo: List<SearchResultViewModel.RenderListItem>) {
        this.hotelBasicInfo = hotelBasicInfo
    }
}

class SearchViewHolder(val binding: SearchListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val hotelName: TextView = itemView.findViewById(R.id.search_hotel_name)
    val price: TextView = itemView.findViewById(R.id.search_price)
    val address: TextView = itemView.findViewById(R.id.search_address)
    val ratingBar: RatingBar = itemView.findViewById(R.id.search_ratingbar)
    val favoriteButton: ImageView = itemView.findViewById(R.id.favorite_button)

    var image: ImageView = itemView.findViewById(R.id.search_image)

}



