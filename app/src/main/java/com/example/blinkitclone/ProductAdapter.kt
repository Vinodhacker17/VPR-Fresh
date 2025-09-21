package com.example.blinkitclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class ProductAdapter(
    private var productList: MutableList<Product>,
    private val context: Context
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.product_name)
        val description: TextView = itemView.findViewById(R.id.product_description)
        val price: TextView = itemView.findViewById(R.id.product_price)
        val image: ImageView = itemView.findViewById(R.id.product_image)
        val addButton: MaterialButton = itemView.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.name.text = product.name
        holder.description.text = product.description
        holder.price.text = product.price
        Glide.with(context).load(product.imageUrl).into(holder.image)

        // --- THIS IS THE UPDATED LOGIC ---
        holder.addButton.setOnClickListener {
            Cart.addItem(product)
            Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList.toMutableList()
        notifyDataSetChanged()
    }
}