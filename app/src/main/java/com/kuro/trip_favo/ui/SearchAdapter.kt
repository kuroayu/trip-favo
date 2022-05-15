package com.kuro.trip_favo.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kuro.trip_favo.data.api.HotelBasicInfo
import com.kuro.trip_favo.databinding.SearchListItemBinding
import com.kuro.trip_favo.ui.viewModel.SearchResultViewModel

class SearchAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<SearchResultViewModel.RenderListItem, SearchViewHolder>(SearchResultDiffItemCallback()) {

    private lateinit var listener: OnItemClickListener
//    lateinit var buttonListener: OnButtonClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = SearchListItemBinding.inflate(inflater, parent, false)
        return SearchViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val hotelData = getItem(position)
        holder.binding.lifecycleOwner = lifecycleOwner
        holder.binding.item = hotelData
        holder.binding.searchImage.load(hotelData.hotelBasicInfo.hotelImageUrl)

        holder.binding.favoriteButton.setOnClickListener {
            listener.onFavoriteButtonClick(it, hotelData)
            Log.d("favoriteButton", "favoriteButton")
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(it, position, hotelData.hotelBasicInfo)
        }
        holder.binding.executePendingBindings()

//        Handler(Looper.getMainLooper()).postDelayed({
//            holder.binding.item = hotelData.copy(isRegistered = true)
//            holder.binding.executePendingBindings()
//        }, 2000)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, data: HotelBasicInfo)
        fun onFavoriteButtonClick(
            favoriteButton: View,
            renderListItem: SearchResultViewModel.RenderListItem
        )
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}

class SearchViewHolder(val binding: SearchListItemBinding) : RecyclerView.ViewHolder(binding.root)

class SearchResultDiffItemCallback : DiffUtil.ItemCallback<SearchResultViewModel.RenderListItem>() {
    override fun areItemsTheSame(
        oldItem: SearchResultViewModel.RenderListItem,
        newItem: SearchResultViewModel.RenderListItem
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: SearchResultViewModel.RenderListItem,
        newItem: SearchResultViewModel.RenderListItem
    ): Boolean = oldItem.hotelBasicInfo.hotelNo == newItem.hotelBasicInfo.hotelNo
            && oldItem.isRegistered == newItem.isRegistered

}


