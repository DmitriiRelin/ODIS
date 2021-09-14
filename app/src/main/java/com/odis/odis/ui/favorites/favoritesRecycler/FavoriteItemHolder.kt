package com.odis.odis.ui.favorites.favoritesRecycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odis.odis.R
import com.odis.odis.databinding.ItemFavoriteBinding
import com.odis.odis.domain.entities.PictureOfDay
import com.zerobranch.layout.SwipeLayout

class FavoriteItemHolder(
    itemView: View,
    private val onItemClickListenerFavorites: FavoritesRecyclerAdapter.OnItemClickListenerFavorites,
    private val onItemClickDelete: FavoritesRecyclerAdapter.OnItemClickListenerDelete
) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemFavoriteBinding = ItemFavoriteBinding.bind(itemView)

    fun bindData(pictureOfDay: PictureOfDay) {
        binding.titleTextView.text = pictureOfDay.title
        binding.dateTextView.text = pictureOfDay.date
        if (pictureOfDay.mediaType == "video") {
            Glide
                .with(itemView.context)
                .load(R.drawable.ic_baseline_play_circle_outline_24)
                .fitCenter()
                .centerCrop()
                .into(binding.imageView)
        } else {
            Glide
                .with(itemView.context)
                .load(pictureOfDay.url)
                .fitCenter()
                .centerCrop()
                .into(binding.imageView)
        }

        if (binding.rightView != null) {
            binding.rightView.setOnClickListener(View.OnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickDelete.onItemClickDelete(pictureOfDay)
                }
            })
        }

        binding.swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
            override fun onOpen(direction: Int, isContinuous: Boolean) {
                if (direction == SwipeLayout.LEFT && isContinuous) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {

                    }
                }
            }

            override fun onClose() {

            }

        })

        binding.dragItem.setOnClickListener {
            onItemClickListenerFavorites.onItemClick(pictureOfDay)
        }
    }

}