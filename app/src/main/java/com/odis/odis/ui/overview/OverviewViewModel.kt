package com.odis.odis.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.odis.odis.Utils.BASE_PAGE_COUNT_FOR_PAGING
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.domain.usecases.GetListPicturesOfDayUseCase
import com.odis.odis.ui.overview.overviewRecycler.PictureSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getListPicturesOfDayUseCase: GetListPicturesOfDayUseCase
) : ViewModel() {

    fun getList(): Flow<PagingData<PictureOfDay>> {
        return Pager(config = PagingConfig(pageSize = BASE_PAGE_COUNT_FOR_PAGING),
            pagingSourceFactory = { PictureSource(getListPicturesOfDayUseCase) }).flow.cachedIn(
            viewModelScope
        )
    }

}