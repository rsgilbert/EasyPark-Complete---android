package com.gilboot.easypark.driverlogin

import com.gilboot.easypark.parksignup.ParkinfoViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gilboot.easypark.Repository

@Suppress("UNCHECKED_CAST")
class DriverloginViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        DriverloginViewModel(repository) as T
}