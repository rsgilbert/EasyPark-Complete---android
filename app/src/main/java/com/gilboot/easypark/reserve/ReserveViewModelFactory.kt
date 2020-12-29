package com.gilboot.easypark.reserve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.reservelist.ReserveListViewModel

@Suppress("UNCHECKED_CAST")
class ReserveViewModelFactory(private val reserve: Visit) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ReserveViewModel(reserve) as T
}