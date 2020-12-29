package com.gilboot.easypark.reservelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository

@Suppress("UNCHECKED_CAST")
class ReserveListViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ReserveListViewModel(repository) as T
}