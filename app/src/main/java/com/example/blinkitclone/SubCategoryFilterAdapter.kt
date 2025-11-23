package com.example.blinkitclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubCategoryFilterAdapter(
    private val subcategories: List<String>,
    private val onSubCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<SubCategoryFilterAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.subcategory_name)
        val icon: ImageView = itemView.findViewById(R.id.subcategory_icon)

        fun bind(subcategoryName: String) {
            name.text = subcategoryName
            // You can add logic here to show different icons
            itemView.setOnClickListener { onSubCategoryClick(subcategoryName) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subcategory_filter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subcategories[position])
    }

    override fun getItemCount() = subcategories.size
}