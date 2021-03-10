package com.example.allcountriesapp.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.allcountriesapp.viewmodels.AllCountriesViewModel

class AllCountriesViewModelFactory(private val app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllCountriesViewModel(app) as T
    }
}