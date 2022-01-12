package com.georgemcdonnell.biirr

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("image_url")
fun loadImage(view: ImageView, url: String?) {
    val beerDrawable = ContextCompat.getDrawable(view.context, R.drawable.beer)
    val colorDrawable = ColorDrawable(Color.WHITE)

    Picasso.get()
        .load(url)
        .placeholder(beerDrawable ?: colorDrawable)
        .into(view)

}

@BindingAdapter("image_drawable")
fun setImageDrawable(view: ImageView, resourceId: Int?) {
    resourceId?.let { view.setImageResource(it) }
}