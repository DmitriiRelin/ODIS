package com.odis.odis.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odis.odis.ResponseResult
import com.odis.odis.domain.entities.PictureOfDay
import com.odis.odis.domain.usecases.AddToFavoriteUseCase
import com.odis.odis.domain.usecases.GetListPicturesOfDayUseCase
import com.odis.odis.domain.usecases.GetPictureOfDayUseCase
import com.odis.odis.domain.usecases.RemoveFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val getPictureOfDayUseCase: GetPictureOfDayUseCase,
    private val getListPicturesOfDayUseCase: GetListPicturesOfDayUseCase
) : ViewModel() {

    private val _pictureLiveData = MutableLiveData<ResponseResult<PictureOfDay>>()
    val pictureLiveData: LiveData<ResponseResult<PictureOfDay>> = _pictureLiveData

    private val _seeAlsoLiveData = MutableLiveData<ResponseResult<List<PictureOfDay>>>()
    val seeAlsoLiveData: LiveData<ResponseResult<List<PictureOfDay>>> = _seeAlsoLiveData


    fun getListSeeAlso() {
        viewModelScope.launch {
            val flow = getListPicturesOfDayUseCase.invoke()
            flow.collect {
                _seeAlsoLiveData.value = it
            }
        }
    }


    fun getStartPicture() {
        viewModelScope.launch {
            getPictureOfDayUseCase.invoke().collect {
                _pictureLiveData.value = it
            }
        }
    }

    fun favoritesClick() {
        viewModelScope.launch {
            pictureLiveData.value?.let { response ->
                if (response is ResponseResult.Success) {
                    val picture = response.data
                    if (picture.inFavorite) {
                        removeFromFavoriteUseCase(picture)
                        _pictureLiveData.value =
                            ResponseResult.Success(picture.copy(inFavorite = false))
                    } else {
                        addToFavoriteUseCase.invoke(picture)
                        _pictureLiveData.value =
                            ResponseResult.Success(picture.copy(inFavorite = true))
                    }
                }
            }

        }
    }

    fun getPictureFromOverview(pictureOfDay: PictureOfDay) {
        viewModelScope.launch {
            _pictureLiveData.value = ResponseResult.Success(pictureOfDay)
        }
    }


    fun getPictureFromCalendar(date: String) {
        viewModelScope.launch {
            val dateResult = getPictureOfDayUseCase.invoke(date)
            dateResult.collect {
                _pictureLiveData.value = it
            }

        }
    }

    fun clickOnSeeAlso(pictureOfDay: PictureOfDay) {
        viewModelScope.launch {
            _pictureLiveData.value = ResponseResult.Success(pictureOfDay)
        }

    }

}