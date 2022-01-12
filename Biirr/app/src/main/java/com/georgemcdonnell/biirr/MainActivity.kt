package com.georgemcdonnell.biirr

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), BeerAdapter.DetailClickListener {

    private val viewModel = BiirrViewModel()

    /** Background coroutine scope */
    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backgroundScope.launch {
            try {
                val beers = viewModel.getBeers()
                withContext(Dispatchers.Main) {
                    initialiseRecyclerAdapter(beers)
                }
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "${throwable.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun initialiseRecyclerAdapter(beers: List<BeerViewModel>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
            adapter = BeerAdapter(beers, this@MainActivity)
        }
    }

    // DetailClickListener

    override fun displayDetail(beerViewModel: BeerViewModel) {
        val intent = Intent(this, BeerDetailActivity::class.java).apply {
            putExtra("viewModel", beerViewModel)
        }
        startActivity(intent)
    }

}
