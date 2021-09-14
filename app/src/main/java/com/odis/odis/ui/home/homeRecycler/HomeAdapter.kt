package com.odis.odis.ui.home.homeRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.odis.odis.R
import com.odis.odis.domain.entities.PictureOfDay

class HomeAdapter(private val listener: OnItemClickListenerSeeAlso) :
    RecyclerView.Adapter<HomeHolder>() {

    interface OnItemClickListenerSeeAlso {
        fun clickOnSeeAlso(pictureOfDay: PictureOfDay)
    }

    var seeAlsoList = listOf<PictureOfDay>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_see_also, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val picture = seeAlsoList[position]
        holder.onBind(picture)
        holder.itemView.setOnClickListener {
            listener.clickOnSeeAlso(picture)
        }
    }

    override fun getItemCount(): Int = seeAlsoList.size
}