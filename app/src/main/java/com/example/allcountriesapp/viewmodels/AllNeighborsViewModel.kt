package com.example.allcountriesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.allcountriesapp.data.Country
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class AllNeighborsViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val TAG = AllNeighborsViewModel::class.java.simpleName
    private val neighbors: MutableLiveData<List<Country>> = MutableLiveData()

    fun getNeighbors(): LiveData<List<Country>> {
        return neighbors
    }

    fun loadNeighbors(countryNeighbors: ArrayList<String>, countryList: ArrayList<Country>) = viewModelScope.launch {
        Log.d(TAG, "loadNeighbors: countryNeighbors= $countryNeighbors")
        val neighborsList = arrayListOf<Country>()

        for(i in countryNeighbors.indices) {
            for(country in countryList) {
                if(country.alpha3Code == countryNeighbors[i])
                    neighborsList.add(country)
            }
        }
        neighbors.postValue(neighborsList)
    }
    

}
