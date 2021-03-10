package com.example.allcountriesapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.allcountriesapp.adapters.AllCountriesAdapter
import com.example.allcountriesapp.factories.AllCountriesViewModelFactory
import com.example.allcountriesapp.viewmodels.AllCountriesViewModel


class AllCountriesActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TAG = AllCountriesActivity::class.java.simpleName

    private lateinit var viewModel: AllCountriesViewModel
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var countriesAdapter: AllCountriesAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_countries)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.toolbar_review_all_countries)
        }

        spinner = findViewById(R.id.sort_method_spinner)
        spinner.onItemSelectedListener = this

        countriesAdapter = AllCountriesAdapter()
        recyclerView = findViewById(R.id.countries_recycler_view)
        recyclerView.apply {
            adapter = countriesAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }

        initViewModel()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        viewModel.onSortMethodSelected(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, AllCountriesViewModelFactory(application)).get(
            AllCountriesViewModel::class.java).also { it.loadCountries() }

        viewModel.getCountries().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                val emptyNeighborsTextView = findViewById<TextView>(R.id.countries_empty)
                emptyNeighborsTextView.isVisible = true
                return@Observer
            } else {
                countriesAdapter.submitList(null) //Handle the ignored case when the same list is submitted with different order
                countriesAdapter.submitList(it)
                countriesAdapter.notifyDataSetChanged()
            }
        })
    }

}