package com.odis.odis.Utils

import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.ui.favorites.favoritesRecycler.PictureWithHeader

fun convertToListWithHeaders(listInter: List<PictureOfDay>): MutableList<PictureWithHeader> {
    val newList: MutableList<PictureWithHeader> = listInter
        .map {
            PictureWithHeader(it, false)
        }
        .toMutableList()
    if (newList.isNotEmpty()) {
        for (i in (newList.size - 1) downTo 1) {
            if (newList[i].pictureOfDay.date.substring(
                    0,
                    7
                ) != newList[i - 1].pictureOfDay.date.substring(0, 7)
            ) {
                val addElement = newList[i].copy(flagHeader = true)
                newList.add(i, addElement)
            }
        }
        val addElement = newList[0].copy(flagHeader = true)
        newList.add(0, addElement)
    }
    return newList
}
