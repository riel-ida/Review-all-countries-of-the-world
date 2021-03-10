package com.example.allcountriesapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.allcountriesapp.adapters.AllNeighborsAdapter
import com.example.allcountriesapp.data.Country
import com.example.allcountriesapp.factories.AllNeighborsViewModelFactory
import com.example.allcountriesapp.viewmodels.AllNeighborsViewModel


class AllNeighborsActivity : AppCompatActivity() {


    private lateinit var viewModel: AllNeighborsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var neighborsAdapter: AllNeighborsAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var selectedCountry: Country
    private lateinit var countryList: ArrayList<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_neighbors)

        selectedCountry = if(intent.hasExtra(INTENT_SELECTED_COUNTRY))
            intent.getSerializableExtra(INTENT_SELECTED_COUNTRY)!! as Country
        else Country("", "", "", "", arrayListOf<String>())

        countryList = if (intent.hasExtra(INTENT_COUNTRY_LIST))
            intent.getSerializableExtra(INTENT_COUNTRY_LIST) as ArrayList<Country>
        else arrayListOf<Country>()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.toolbar_review_all_neighbors).plus(
                " ${selectedCountry.name}"
            )
        }

        neighborsAdapter = AllNeighborsAdapter()
        recyclerView = findViewById(R.id.neighbors_recycler_view)
        recyclerView.apply {
            adapter = neighborsAdapter
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, AllNeighborsViewModelFactory(application)).get(
            AllNeighborsViewModel::class.java
        ).also {
            it.loadNeighbors(selectedCountry.borders, countryList)
        }

        viewModel.getNeighbors().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                val emptyNeighborsTextView = findViewById<TextView>(R.id.neighbors_empty)
                emptyNeighborsTextView.isVisible = true
                return@Observer
            } else {
                neighborsAdapter.submitList(it)
                neighborsAdapter.notifyDataSetChanged()
            }
        })
    }

    companion object {
        private val TAG = AllNeighborsActivity::class.java.simpleName

        const val INTENT_SELECTED_COUNTRY = "INTENT_SELECTED_COUNTRY"
        const val INTENT_COUNTRY_LIST = "INTENT_COUNTRY_LIST"

        fun startActivity(context: Context, selectedCountry: Country, countryList: ArrayList<Country>?) {
            val bundle = Bundle()
            val intent = Intent(context, AllNeighborsActivity::class.java)
            intent.putExtra(INTENT_SELECTED_COUNTRY, selectedCountry)
            intent.putExtra(INTENT_COUNTRY_LIST, countryList)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent, bundle)
        }
    }

}