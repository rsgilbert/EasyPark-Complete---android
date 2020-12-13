package com.gilboot.easypark.dashboard


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory(private val parkId: String) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DashboardViewModel(parkId) as T
}