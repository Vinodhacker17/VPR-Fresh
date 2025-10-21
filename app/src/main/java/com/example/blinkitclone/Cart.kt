package com.example.blinkitclone

object Cart {
    private val items = mutableMapOf<Product, Int>()

    fun addItem(product: Product) {
        val currentQuantity = items[product] ?: 0
        items[product] = currentQuantity + 1
    }
    fun getItemCount(): Int {
        // Returns the total number of items (sum of quantities)
        return items.values.sum()
        // Or, if you want the number of *unique* products: return items.size
    }
    fun removeItem(product: Product) {
        items.remove(product)
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
    }

    fun getItemsWithQuantity(): Map<Product, Int> {
        return items.toMap()
    }

    fun getTotalPrice(): Double {
        return items.entries.sumOf { (product, quantity) ->
            (product.price.replace("₹", "").toDoubleOrNull() ?: 0.0) * quantity
        }
    }

    fun clearCart() {
        items.clear()
    }
}