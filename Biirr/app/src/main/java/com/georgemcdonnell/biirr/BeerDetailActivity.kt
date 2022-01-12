package com.georgemcdonnell.biirr

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ekn.gruzer.gaugelibrary.HalfGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.georgemcdonnell.biirr.databinding.BeerDetailBinding
import uk.co.deanwild.flowtextview.FlowTextView

class BeerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: BeerViewModel? = intent.getParcelableExtra("viewModel")

        val binding: BeerDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.beer_detail)

        binding.viewModel = viewModel

        viewModel?.let {
            configureDescription(it)
            configureIBU(it)
        }
    }

    private fun configureDescription(beerViewModel: BeerViewModel) {
        val descriptionTextView: FlowTextView = findViewById(R.id.description_text_view)
        descriptionTextView.setTextSize(48.0F)
        descriptionTextView.text = beerViewModel.beer.style?.description
    }

    private fun configureIBU(beerViewModel: BeerViewModel) {
        val gauge: HalfGauge = findViewById(R.id.ibv_gauge)

        if (beerViewModel.beer.ibu != null) {
            val smooth = Range()
            smooth.color = Color.parseColor("#00b20b")
            smooth.from = 0.0
            smooth.to = 20.0

            val bitter = Range()
            bitter.color = Color.parseColor("#E3E500")
            bitter.from = 20.1
            bitter.to = 50.0

            val hipster = Range()
            hipster.color = Color.parseColor("#ce0000")
            hipster.from = 50.1
            hipster.to = 120.0

            gauge.addRange(smooth)
            gauge.addRange(bitter)
            gauge.addRange(hipster)

            gauge.minValue = 0.0
            gauge.maxValue = 120.0
            gauge.value = beerViewModel.beer.ibu.toDouble()
        }

    }
}
