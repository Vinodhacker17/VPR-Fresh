package com.example.blinkitclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SubCategoryAdapter(private val subCategoryList: List<SubCategory>) :
    RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    class SubCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.subcategory_image)
        val name: TextView = itemView.findViewById(R.id.subcategory_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subcategory, parent, false)
        return SubCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val item = subCategoryList[position]
        holder.name.text = item.name
        holder.image.setImageResource(item.image)
    }

    override fun getItemCount() = subCategoryList.size
}
