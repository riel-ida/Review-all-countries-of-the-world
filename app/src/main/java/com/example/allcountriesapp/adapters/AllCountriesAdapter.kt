package com.example.allcountriesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.allcountriesapp.data.Country
import com.example.allcountriesapp.AllNeighborsActivity
import com.example.allcountriesapp.R

class AllCountriesAdapter : ListAdapter<Country, AllCountriesAdapter.ViewHolder>(DiffCallback()) {

    private val TAG = AllCountriesAdapter::class.java.simpleName
    private var countriesList = arrayListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCountriesAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.country_list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllCountriesAdapter.ViewHolder, position: Int) {
        holder.update(getItem(position))
    }

    override fun submitList(list: List<Country>?) {
        if(list != null) {
            countriesList = arrayListOf<Country>()
            countriesList.addAll(list)
        }
        super.submitList(list)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById<TextView>(R.id.country_name)
        private val countryNativeName = view.findViewById<TextView>(R.id.country_native_name)

        fun update(country: Country) {
            countryName.text = country.name
            countryNativeName.text = country.nativeName
            view.setOnClickListener {
                Log.d(TAG, "OnClickListener: selected country= $country")
                AllNeighborsActivity.startActivity(view.context, country, countriesList)
            }
        }
    }
}

    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }
