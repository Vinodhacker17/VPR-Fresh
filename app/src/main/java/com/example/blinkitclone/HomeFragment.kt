package com.example.blinkitclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private val allProducts = createProductList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        val categoryTabs = view.findViewById<TabLayout>(R.id.category_tabs)
        val profileIcon = view.findViewById<ImageView>(R.id.user_profile_icon)

        profileIcon.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        productAdapter = ProductAdapter(allProducts.toMutableList(), requireContext())
        productRecyclerView.adapter = productAdapter

        val categories = getCategories()
        for (category in categories) {
            categoryTabs.addTab(categoryTabs.newTab().setText(category))
        }

        categoryTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedCategory = tab?.text.toString()
                val filteredList = if (selectedCategory == "All") {
                    allProducts
                } else {
                    allProducts.filter { it.category == selectedCategory }
                }
                productAdapter.updateList(filteredList)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return view
    }

    private fun getCategories(): List<String> {
        return listOf(
            "All", "Pharmacy", "Electronics", "Beauty", "Decor", "Kids", "Gifting", "Premium"
        )
    }

    // --- EXPANDED PRODUCT LIST ---
    private fun createProductList(): List<Product> {
        return listOf(
            // All / Groceries
            Product("Cow Milk", "1 litre", "₹55", "https://i.imgur.com/8L5qK6M.png", "All"),
            Product("Brown Bread", "1 packet", "₹45", "https://i.imgur.com/x0g34b9.png", "All"),
            Product("Farm Eggs", "6 pieces", "₹70", "https://i.imgur.com/wVv5z8z.png", "All"),
            Product("Ripe Tomatoes", "1 kg", "₹40", "https://i.imgur.com/3z3b6cf.png", "All"),
            Product("Fresh Carrots", "500 g", "₹30", "https://i.imgur.com/s7z4x1b.png", "All"),
            Product("Spinach Bunch", "250 g", "₹25", "https://i.imgur.com/0f4b3e9.png", "All"),

            // Pharmacy
            Product("Aspirin", "10 tablets", "₹20", "https://i.imgur.com/t2y7gXw.png", "Pharmacy"),
            Product("Band-Aids", "20 strips", "₹50", "https://i.imgur.com/uNf7kL4.png", "Pharmacy"),
            Product("Cough Syrup", "100 ml", "₹120", "https://i.imgur.com/rG4Y3hL.png", "Pharmacy"),

            // Beauty
            Product("Lipstick", "Matte Red", "₹450", "https://i.imgur.com/7gN6sYQ.png", "Beauty"),
            Product("Mascara", "Volumizing", "₹300", "https://i.imgur.com/8hT8zSg.png", "Beauty"),
            Product("Sunscreen", "SPF 50", "₹550", "https://i.imgur.com/J3Z3pQZ.png", "Beauty"),

            // Electronics
            Product("Wireless Mouse", "2.4GHz Optical", "₹800", "https://i.imgur.com/K4g1zLq.png", "Electronics"),
            Product("Earbuds", "Bluetooth 5.1", "₹1500", "https://i.imgur.com/D4s2BfJ.png", "Electronics"),
            Product("Power Bank", "10000mAh", "₹1200", "https://i.imgur.com/3w3VvNq.png", "Electronics"),

            // Decor
            Product("Scented Candle", "Lavender", "₹350", "https://i.imgur.com/6JzYpWb.png", "Decor"),
            Product("Photo Frame", "6x4 inch", "₹250", "https://i.imgur.com/9nO2wKb.png", "Decor"),

            // Kids
            Product("Toy Car", "Die-cast model", "₹400", "https://i.imgur.com/5hU5pXf.png", "Kids"),
            Product("Crayons", "12 pack", "₹80", "https://i.imgur.com/2cR3sJd.png", "Kids"),

            // Gifting
            Product("Gift Wrap", "2 meters", "₹100", "https://i.imgur.com/mN1tZJk.png", "Gifting"),
            Product("Chocolate Box", "Assorted", "₹600", "https://i.imgur.com/1lP2sYn.png", "Gifting"),

            // Premium
            Product("Imported Cheese", "Gouda, 200g", "₹750", "https://i.imgur.com/oV7sD2M.png", "Premium"),
            Product("Dark Chocolate", "70% Cacao", "₹400", "https://i.imgur.com/sK3yXNj.png", "Premium")
        )
    }
}

