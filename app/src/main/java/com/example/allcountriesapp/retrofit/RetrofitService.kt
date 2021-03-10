package com.example.allcountriesapp.retrofit

import com.example.allcountriesapp.data.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    //https://restcountries.eu/rest/v2/all?fields=name;nativeName;alpha3Code;area;borders
    @GET("all")
    fun getDataFromAPI(@Query("fields") fields: String): Call<ArrayList<Country>>
}