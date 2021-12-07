package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.models.PictureOfDay
import com.udacity.asteroidradar.databinding.ItemHeaderBinding
import com.udacity.asteroidradar.utils.loadImage

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.ImageOfDayViewHolder>() {
    private val list = arrayListOf<PictureOfDay>()

    class ImageOfDayViewHolder(private var binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pictureOfDay: PictureOfDay) {
            pictureOfDay.url?.let { binding.imageOfDay.loadImage(it) }
            binding.imageOfDay.contentDescription = binding.root.context.getString(
                R.string.nasa_picture_of_day_content_description_format,
                pictureOfDay.title
            )
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageOfDayViewHolder {
        return ImageOfDayViewHolder(
            ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageOfDayViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(vararg pictureOfDay: PictureOfDay) {
        list.clear()
        pictureOfDay.map {
            list.add(it)
        }
        notifyDataSetChanged()
    }
}
