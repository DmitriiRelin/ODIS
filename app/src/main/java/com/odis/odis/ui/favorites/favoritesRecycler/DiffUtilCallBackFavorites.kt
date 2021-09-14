package com.odis.odis.ui.favorites.favoritesRecycler

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBackFavorites : DiffUtil.ItemCallback<PictureWithHeader>() {

    override fun areItemsTheSame(oldItem: PictureWithHeader, newItem: PictureWithHeader): Boolean {
        return oldItem.pictureOfDay.date == newItem.pictureOfDay.date
    }

    override fun areContentsTheSame(
        oldItem: PictureWithHeader,
        newItem: PictureWithHeader
    ): Boolean {
        return oldItem == newItem
    }

}