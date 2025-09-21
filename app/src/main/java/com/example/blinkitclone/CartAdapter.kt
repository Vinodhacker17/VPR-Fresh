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
    private var cartItems: MutableList<Product>, // Make list mutable
    private val context: Context,
    private val onRemoveClicked: (Product) -> Unit // Callback for removing item
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateList(newList: List<Product>) {
        cartItems.clear()
        cartItems.addAll(newList)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cart_item_image)
        val name: TextView = itemView.findViewById(R.id.cart_item_name)
        val description: TextView = itemView.findViewById(R.id.cart_item_description)
        val price: TextView = itemView.findViewById(R.id.cart_item_price)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.name.text = item.name
        holder.description.text = item.description
        holder.price.text = item.price
        Glide.with(context).load(item.imageUrl).into(holder.image)

        holder.deleteButton.setOnClickListener {
            onRemoveClicked(item)
        }
    }

    override fun getItemCount() = cartItems.size
}