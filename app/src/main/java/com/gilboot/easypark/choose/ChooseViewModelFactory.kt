package com.gilboot.easypark.choose


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository

@Suppress("UNCHECKED_CAST")
class ChooseViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ChooseViewModel(repository) as T
}