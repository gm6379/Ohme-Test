package com.georgemcdonnell.biirr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.georgemcdonnell.biirr.databinding.BeerCellBinding

class BeerAdapter(
    private val beers: List<BeerViewModel>,
    private val listener: DetailClickListener
): RecyclerView.Adapter<BeerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BeerCellBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(beers[position])
        holder.itemView.setOnClickListener { listener.displayDetail(beers[position]) }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(private val binding: BeerCellBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BeerViewModel) {
            binding.viewModel = item
            binding.executePendingBindings()
        }
    }

    interface DetailClickListener {
        fun displayDetail(beerViewModel: BeerViewModel)
    }

}