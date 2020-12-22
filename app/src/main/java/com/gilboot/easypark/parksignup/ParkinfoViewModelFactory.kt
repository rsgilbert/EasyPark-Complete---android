package com.gilboot.easypark.parksignup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository

@Suppress("UNCHECKED_CAST")
class ParkinfoViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ParkinfoViewModel(repository) as T
}