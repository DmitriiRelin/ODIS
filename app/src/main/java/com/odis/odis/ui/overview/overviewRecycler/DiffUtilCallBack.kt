package com.odis.odis.ui.overview.overviewRecycler

import androidx.recyclerview.widget.DiffUtil
import com.odis.odis.domain.entities.PictureOfDay

class DiffUtilCallBack : DiffUtil.ItemCallback<PictureOfDay>() {

    override fun areItemsTheSame(oldItem: PictureOfDay, newItem: PictureOfDay): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: PictureOfDay, newItem: PictureOfDay): Boolean {
        return oldItem == newItem
    }

}