package com.example.blinkitclone

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Cart {
    private var items = mutableMapOf<Product, Int>()
    private lateinit var preferences: SharedPreferences
    private val gson = Gson()

    // --- NEW: Initialize and Load Data ---
    fun init(context: Context) {
        preferences = context.getSharedPreferences("MyCartPrefs", Context.MODE_PRIVATE)
        loadCart()
    }

    fun addItem(product: Product) {
        val currentQuantity = items[product] ?: 0
        items[product] = currentQuantity + 1
        saveCart() // Save immediately
    }

    fun removeItem(product: Product) {
        items.remove(product)
        saveCart() // Save immediately
    }

    fun increaseQuantity(product: Product) {
        addItem(product)
    }

    fun decreaseQuantity(product: Product) {
        val currentQuantity = items[product] ?: 0
        if (currentQuantity > 1) {
            items[product] = currentQuantity - 1
        } else {
            removeItem(product)
        }
        saveCart() // Save immediately
    }

    fun getItemsWithQuantity(): Map<Product, Int> {
        return items.toMap()
    }

    fun getTotalPrice(): Double {
        return items.entries.sumOf { (product, quantity) ->
            val priceString = product.price.replace("₹", "").replace(",", "").trim()
            (priceString.toDoubleOrNull() ?: 0.0) * quantity
        }
    }

    fun getItemCount(): Int {
        return items.values.sum()
    }

    fun clearCart() {
        items.clear()
        saveCart() // Save the empty state
    }

    // --- INTERNAL FUNCTION: Save to Phone Storage ---
    private fun saveCart() {
        if (::preferences.isInitialized) {
            // Convert Cart Map to a List for saving
            val cartList = items.map { CartEntry(it.key, it.value) }
            val jsonString = gson.toJson(cartList)

            preferences.edit().putString("cart_data", jsonString).apply()
        }
    }

    // --- INTERNAL FUNCTION: Load from Phone Storage ---
    private fun loadCart() {
        if (::preferences.isInitialized) {
            val jsonString = preferences.getString("cart_data", null)
            if (jsonString != null) {
                val type = object : TypeToken<List<CartEntry>>() {}.type
                val cartList: List<CartEntry> = gson.fromJson(jsonString, type)

                items.clear()
                cartList.forEach { entry ->
                    items[entry.product] = entry.quantity
                }
            }
        }
    }
}

// Helper class to store the data
data class CartEntry(val product: Product, val quantity: Int)