package com.example.blinkitclone

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SubCategoryAdapter(private var subCategoryList: MutableList<SubCategory>) :
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

        // Set the name
        holder.name.text = item.name

        // --- USE GLIDE TO LOAD ONLINE IMAGE ---
        Glide.with(holder.itemView.context)
            .load(item.imageUrl) // Load from the URL string
            .placeholder(R.drawable.ic_search) // Optional: Show this while loading
            .error(R.drawable.ic_search) // Optional: Show this if URL fails
            .into(holder.image)

        // Handle click to open Product List
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductListActivity::class.java)
            intent.putExtra("categoryName", item.name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = subCategoryList.size

    // Function to update list for Search functionality
    fun updateList(newList: List<SubCategory>) {
        subCategoryList.clear()
        subCategoryList.addAll(newList)
        notifyDataSetChanged()
    }
}