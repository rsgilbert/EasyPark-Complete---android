package com.gilboot.easypark.park


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Park

@Suppress("UNCHECKED_CAST")
class ParkViewModelFactory(private val parkId: String, private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ParkViewModel(parkId, repository) as T
}