package com.udacity.asteroidradar.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R

fun ImageView.loadImage(imageUrl: String, errorDrawableRes: Int = R.drawable.image_of_day_default) {
    Picasso.with(context).load(imageUrl).error(errorDrawableRes).into(this)
}
