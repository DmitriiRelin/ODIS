package com.odis.odis.ui.favorites.favoritesRecycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.odis.odis.databinding.ItemFavoriteHeaderBinding
import com.odis.odis.domain.entities.PictureOfDay

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val START_INDEX_FOR_SUBSTRING = 0
        private const val END_INDEX_FOR_SUBSTRING = 3
    }

    private val binding: ItemFavoriteHeaderBinding = ItemFavoriteHeaderBinding.bind(itemView)

    fun bindData(pictureOfDay: PictureOfDay) {
        binding.headerView.text = (pictureOfDay.date).substring(START_INDEX_FOR_SUBSTRING, pictureOfDay.date.length - END_INDEX_FOR_SUBSTRING)
    }
}