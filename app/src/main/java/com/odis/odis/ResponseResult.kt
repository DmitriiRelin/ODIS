package com.odis.odis

sealed class ResponseResult<out R> {
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Error(val error: Throwable) : ResponseResult<Nothing>()
    data class Loading(val progress: Int? = null) : ResponseResult<Nothing>()
}