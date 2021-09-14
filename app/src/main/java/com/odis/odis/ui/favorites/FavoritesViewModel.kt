package com.odis.odis.ui.favorites

import androidx.lifecycle.*
import com.odis.odis.Utils.SIMPLE_DATE_FORMAT
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.domain.usecases.GetFavoriteListUseCase
import com.odis.odis.domain.usecases.RemoveFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase
) : ViewModel() {

    private val _unfilteredFavoritesLiveData = MutableLiveData<List<PictureOfDay>>()

    private val _filterText = MutableLiveData("")
    fun changeFilterText(string: String) {
        _filterText.value = string
    }

    val favoritesLiveData: LiveData<List<PictureOfDay>> =
        MediatorLiveData<List<PictureOfDay>>().apply {
            addSource(_unfilteredFavoritesLiveData) { value = filterPictureList(it) }
            addSource(_filterText) {
                value = filterPictureList(_unfilteredFavoritesLiveData.value ?: emptyList())
            }
        }


    fun getList() {
        viewModelScope.launch {
            _unfilteredFavoritesLiveData.value = getFavoriteListUseCase.invoke()
                .sortedByDescending { SimpleDateFormat(SIMPLE_DATE_FORMAT).parse(it.date) }
        }
    }

    fun deleteFavorite(picture: PictureOfDay) {
        viewModelScope.launch {
            removeFromFavoriteUseCase(picture)
            getList()
        }
    }

    private fun filterPictureList(list: List<PictureOfDay>): List<PictureOfDay> =
        list.filter { it.title.contains(_filterText.value ?: "", true) }
}

