package com.gilboot.easypark.reserve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Reserve

@Suppress("UNCHECKED_CAST")
class ReserveViewModelFactory(private val reserve: Reserve) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ReserveViewModel(reserve) as T
}