package com.mahdi.moneymanagemant.feature_management.presentation.util

sealed class Resource<T>{
      data class Success<T>(val data:T):Resource<T>()
      data class Load<T>(val data:T? = null):Resource<T>()
}
