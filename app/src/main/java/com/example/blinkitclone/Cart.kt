package com.example.blinkitclone

object Cart {
    private val cartItems = mutableListOf<Product>()

    fun addItem(product: Product) {
        cartItems.add(product)
    }

    fun removeItem(product: Product) {
        cartItems.remove(product)
    }

    fun getItems(): List<Product> {
        return cartItems.toList()
    }

    fun getTotalPrice(): String {
        val total = cartItems.sumOf { it.price.replace("₹", "").toDoubleOrNull() ?: 0.0 }
        return "₹${"%.2f".format(total)}"
    }

    // --- THIS IS THE MISSING FUNCTION ---
    fun clearCart() {
        cartItems.clear()
    }
}