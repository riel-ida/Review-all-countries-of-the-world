package com.example.allcountriesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.allcountriesapp.data.Country
import com.example.allcountriesapp.retrofit.RetrofitInstance
import com.example.allcountriesapp.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class AllCountriesViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val TAG = AllCountriesViewModel::class.java.simpleName

    private val countries: MutableLiveData<List<Country>> = MutableLiveData()

    fun getCountries(): LiveData<List<Country>> {
        return countries
    }

    fun loadCountries() =
        viewModelScope.launch {
            makeCountriesAPICall("name;nativeName;area;alpha3Code;borders")
        }

    private suspend fun makeCountriesAPICall(fields: String) = withContext(Dispatchers.IO) {
        val retroInstance =
            RetrofitInstance.getRetrofitInstance().create((RetrofitService::class.java))
        val call = retroInstance.getDataFromAPI(fields)
        call.enqueue(object : Callback<ArrayList<Country>> {
            override fun onFailure(call: Call<ArrayList<Country>>, t: Throwable) {
                Log.d(TAG, "makeCountriesAPICall: onFailure")
                countries.postValue(null)
            }

            override fun onResponse (
                call: Call<ArrayList<Country>>,
                response: Response<ArrayList<Country>>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "makeCountriesAPICall: onResponse-isSuccessful")
                    countries.postValue(response.body())
                } else {
                    countries.postValue(null)
                }
            }
        })
    }

    fun onSortMethodSelected(position: Int) =
        viewModelScope.launch {
            if(position == 0) {
                countries.postValue(getCountries().value?.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name }))
            } else {
                countries.postValue(getCountries().value?.sortedWith(compareBy { it.area }))
            }
        }
    

}
