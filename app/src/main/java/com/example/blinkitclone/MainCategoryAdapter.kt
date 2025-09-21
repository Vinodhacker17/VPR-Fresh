package com.example.blinkitclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainCategoryAdapter(private val mainCategoryList: List<MainCategory>) :
    RecyclerView.Adapter<MainCategoryAdapter.MainCategoryViewHolder>() {

    class MainCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.main_category_title)
        val subCategoryRecyclerView: RecyclerView = itemView.findViewById(R.id.subcategory_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_category, parent, false)
        return MainCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        val mainCategory = mainCategoryList[position]
        holder.title.text = mainCategory.title

        // This is the key part: we set up the inner grid (the SubCategoryAdapter)
        // for each main category row.
        holder.subCategoryRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 4)
        holder.subCategoryRecyclerView.adapter = SubCategoryAdapter(mainCategory.subCategories)
    }

    override fun getItemCount() = mainCategoryList.size
}

