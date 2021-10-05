package com.odis.odis.ui.favorites.favoritesRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.odis.odis.R
import com.odis.odis.Utils.convertToListWithHeaders
import com.odis.odis.domain.entities.PictureOfDay

class FavoritesRecyclerAdapter(
    private val onItemClickListenerFavorites: OnItemClickListenerFavorites,
    private val onItemClickDelete: OnItemClickListenerDelete
) : ListAdapter<PictureWithHeader, RecyclerView.ViewHolder>(DiffUtilCallBackFavorites()) {

    companion object {
        private const val VIEW_TYPE_FAVORITE_ITEM = 0
        private const val VIEW_TYPE_FAVORITE_HEADER = 1
    }

    interface OnItemClickListenerFavorites {
        fun onItemClick(picture: PictureOfDay)
    }

    interface OnItemClickListenerDelete {
        fun onItemClickDelete(picture: PictureOfDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_FAVORITE_ITEM -> FavoriteItemHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_favorite, parent, false),
                onItemClickListenerFavorites,
                onItemClickDelete
            )
            else ->
                HeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_favorite_header, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoriteItemHolder) {
            holder.bindData(getItem(position).pictureOfDay)
        } else if (holder is HeaderViewHolder) {
            holder.bindData(getItem(position).pictureOfDay)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).flagHeader) {
            VIEW_TYPE_FAVORITE_HEADER
        } else VIEW_TYPE_FAVORITE_ITEM
    }

    fun setList(list: List<PictureOfDay>) {
        val listWithHeaders = convertToListWithHeaders(list)
        submitList(listWithHeaders)
    }

}