package com.odis.odis.ui.overview.overviewRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.odis.odis.R
import com.odis.odis.domain.entities.PictureOfDay

class OverviewAdapter(private val onItemClickListenerOverview: OnItemClickListenerOverview) :
    PagingDataAdapter<PictureOfDay, OverviewHolder>(
        DiffUtilCallBack()
    ) {

    interface OnItemClickListenerOverview {
        fun onItemClick(picture: PictureOfDay)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewHolder {
        return OverviewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_overview, parent, false),
            onItemClickListenerOverview

        )
    }

    override fun onBindViewHolder(holder: OverviewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}