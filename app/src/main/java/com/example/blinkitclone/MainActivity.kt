package com.example.blinkitclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productRecyclerView: RecyclerView = findViewById(R.id.product_recycler_view)
        productRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns

        val productList = getProductList() // Get mock data with real image URLs
        val adapter = ProductAdapter(productList, this)
        productRecyclerView.adapter = adapter
    }

    // This function now provides a list of products with real, working image URLs.
    private fun getProductList(): List<Product> {
        return listOf(
            Product("Fresh Apple", "1 kg", "₹120", "https://images.pexels.com/photos/102104/pexels-photo-102104.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
            Product("Organic Banana", "1 dozen", "₹60", "https://i.postimg.cc/Wpf2BKgr/OIP.webp"),
            Product("Cow Milk", "1 Litre", "₹55", "https://i.postimg.cc/HsH2CZmY/milk-packet-png-9.png"),
            Product("Brown Bread", "1 packet", "₹45", "https://i.postimg.cc/zvkP14Ph/OIP1.webp"),
            Product("Farm Eggs", "6 pieces", "₹70", "https://images.pexels.com/photos/162712/egg-white-food-protein-162712.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
            Product("Ripe Tomatoes", "1 kg", "₹40", "https://images.pexels.com/photos/1327838/pexels-photo-1327838.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
            Product("Fresh Carrots", "500 g", "₹30", "https://images.pexels.com/photos/1306559/pexels-photo-1306559.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
            Product("Spinach Bunch", "250 g", "₹25", "https://images.pexels.com/photos/2325843/pexels-photo-2325843.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1")
        )
    }
}

