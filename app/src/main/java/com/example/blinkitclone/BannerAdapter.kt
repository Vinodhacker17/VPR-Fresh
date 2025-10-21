package com.example.blinkitclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BannerAdapter(private val bannerImageUrls: List<String>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.banner_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner_slide, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // Calculate the real position within your actual banner list
        val actualPosition = position % bannerImageUrls.size
        Glide.with(holder.itemView.context)
            .load(bannerImageUrls[actualPosition])
            .into(holder.imageView)
    }

    // Return a very large number to simulate infinite scrolling
    override fun getItemCount(): Int {
        // Return Int.MAX_VALUE only if you have images, otherwise 0
        return if (bannerImageUrls.isNotEmpty()) Int.MAX_VALUE else 0
    }
}