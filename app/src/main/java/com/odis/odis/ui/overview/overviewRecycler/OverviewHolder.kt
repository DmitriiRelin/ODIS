package com.odis.odis.ui.overview.overviewRecycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.odis.odis.R
import com.odis.odis.databinding.ItemOverviewBinding
import com.odis.odis.domain.entities.PictureOfDay

class OverviewHolder(
    view: View,
    private val onItemClickListenerOverview: OverviewAdapter.OnItemClickListenerOverview
) :
    RecyclerView.ViewHolder(view) {

    private val binding: ItemOverviewBinding = ItemOverviewBinding.bind(itemView)

    fun bind(picture: PictureOfDay) {
        Glide
            .with(itemView.context)
            .load(picture.url)
            .placeholder(R.drawable.placeholder_background)
            .fitCenter()
            .centerCrop()
            .into(binding.imagePost)

        binding.imagePost.setOnClickListener {
            onItemClickListenerOverview.onItemClick(picture)
        }
    }
}