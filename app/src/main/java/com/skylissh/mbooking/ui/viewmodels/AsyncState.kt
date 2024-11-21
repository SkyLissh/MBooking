package com.skylissh.mbooking.ui.viewmodels

sealed interface AsyncState<out T> {
  data class Success<T>(val data: T) : AsyncState<T>
  object Loading : AsyncState<Nothing>
  data class Error(val message: String) : AsyncState<Nothing>
}
