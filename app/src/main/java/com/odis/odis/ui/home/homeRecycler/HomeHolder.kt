package com.odis.odis.ui.home.homeRecycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odis.odis.R
import com.odis.odis.databinding.ItemSeeAlsoBinding
import com.odis.odis.domain.entities.PictureOfDay

class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val VIDEO_MEDIA_TYPE = "video"
    }

    private val binding: ItemSeeAlsoBinding = ItemSeeAlsoBinding.bind(itemView)

    fun onBind(picture: PictureOfDay) {
        if (picture.mediaType == VIDEO_MEDIA_TYPE) {
            Glide.with(itemView.context)
                .load(R.drawable.ic_baseline_play_circle_outline_24)
                .placeholder(R.color.primaryColor)
                .fitCenter()
                .centerCrop()
                .into(binding.seeAlsoImage)
        } else {
            Glide.with(itemView.context)
                .load(picture.url)
                .placeholder(R.color.primaryColor)
                .fitCenter()
                .centerCrop()
                .into(binding.seeAlsoImage)
        }
    }

}