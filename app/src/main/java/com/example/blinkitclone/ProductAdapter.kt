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

    fun updateList(newList: List<Product>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productDescription: TextView = itemView.findViewById(R.id.product_description)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val addToCartButton: MaterialButton = itemView.findViewById(R.id.add_to_cart_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = product.price

        Glide.with(context)
            .load(product.imageUrl)
            .into(holder.productImage)

        // --- THIS IS THE NEW CODE ---
        // We add a click listener to the button
        holder.addToCartButton.setOnClickListener {
            // Show a simple message when the button is clicked
            Toast.makeText(context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
        }
        // --------------------------
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

