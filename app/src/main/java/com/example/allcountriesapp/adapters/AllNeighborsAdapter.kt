package com.example.allcountriesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.allcountriesapp.data.Country
import com.example.allcountriesapp.R

class AllNeighborsAdapter : ListAdapter<Country, AllNeighborsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.country_list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.update(getItem(position))
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById<TextView>(R.id.country_name)
        private val countryNativeName = view.findViewById<TextView>(R.id.country_native_name)

        fun update(country: Country) {
            countryName.text = country.name
            countryNativeName.text = country.nativeName
        }
    }
}
