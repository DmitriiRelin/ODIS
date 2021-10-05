package com.odis.odis.ui.overview.overviewRecycler

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.domain.usecases.GetListPicturesOfDayUseCase

class PictureSource(
    private val getListPicturesOfDayUseCase: GetListPicturesOfDayUseCase
) : PagingSource<Int, PictureOfDay>() {

    companion object {
        private const val PAGING_EXCEPTION = "Unknown error: no list in map"
        private  const val PAGE_INDEX_ZERO = 0
        private const val PAGE_INDEX_ONE = 1
    }

    private val map = hashMapOf<Int, List<PictureOfDay>>()
    override fun getRefreshKey(state: PagingState<Int, PictureOfDay>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PictureOfDay> {
        return try {
            val currentPage: Int = params.key ?: PAGE_INDEX_ZERO
            if (!map.containsKey(currentPage)) {
                val list = getListPicturesOfDayUseCase.getList()
                map[currentPage] = list
            }
            val list =
                map[currentPage] ?: throw  java.lang.Exception(PAGING_EXCEPTION)
            LoadResult.Page(
                data = list,
                prevKey = if (currentPage > PAGE_INDEX_ZERO) currentPage - PAGE_INDEX_ONE else null,
                nextKey = currentPage + PAGE_INDEX_ONE
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}