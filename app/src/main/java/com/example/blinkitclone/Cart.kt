package com.example.blinkitclone

// A Singleton object to manage the shopping cart globally
object Cart {
    private val items = mutableListOf<Product>()

    fun addItem(product: Product) {
        items.add(product)
    }

    fun getItems(): List<Product> {
        return items.toList()
    }

    fun getTotalPrice(): String {
        val total = items.sumOf { it.price.replace("₹", "").toDouble() }
        return "₹${"%.2f".format(total)}"
    }

    fun clearCart() {
        items.clear()
    }
}
