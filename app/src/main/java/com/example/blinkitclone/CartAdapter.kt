package com.example.blinkitclone

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private var cartItems: MutableMap<Product, Int>,
    private val context: Context,
    private val onQuantityChanged: () -> Unit // Callback to update total in the fragment
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateData(newCartItems: Map<Product, Int>) {
        cartItems = newCartItems.toMutableMap()
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cart_item_image)
        val name: TextView = itemView.findViewById(R.id.cart_item_name)
        val description: TextView = itemView.findViewById(R.id.cart_item_description)
        val price: TextView = itemView.findViewById(R.id.cart_item_price)
        val quantityText: TextView = itemView.findViewById(R.id.quantity_text)
        val plusButton: ImageButton = itemView.findViewById(R.id.plus_button)
        val minusButton: ImageButton = itemView.findViewById(R.id.minus_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems.keys.elementAt(position)
        val quantity = cartItems[product]

        holder.name.text = product.name
        holder.description.text = product.description
        holder.price.text = product.price
        holder.quantityText.text = quantity.toString()
        Glide.with(context).load(product.imageUrl).into(holder.image)

        holder.plusButton.setOnClickListener {
            Cart.increaseQuantity(product)
            onQuantityChanged() // Notify the fragment to update the total
        }
        holder.minusButton.setOnClickListener {
            Cart.decreaseQuantity(product)
            onQuantityChanged() // Notify the fragment to update the total
        }
    }

    override fun getItemCount() = cartItems.size
}