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
        val profileIcon = view.findViewById<ImageView>(R.id.user_profile_icon) // Get the profile icon

        // --- THIS IS THE LOGIC FOR STEP 4 ---
        // It makes the profile icon clickable and opens the new screen
        profileIcon.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        // ------------------------------------

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

    private fun createProductList(): List<Product> {
        return listOf(
            Product("Cow Milk", "1 litre", "₹55", "https://i.imgur.com/8L5qK6M.png", "All"),
            Product("Brown Bread", "1 packet", "₹45", "https://i.imgur.com/x0g34b9.png", "All"),
            Product("Aspirin", "10 tablets", "₹20", "https://i.imgur.com/dummy-pharma.png", "Pharmacy"),
            Product("Band-Aids", "20 strips", "₹50", "https://i.imgur.com/dummy-pharma2.png", "Pharmacy"),
            Product("Lipstick", "Matte Red", "₹450", "https://i.imgur.com/dummy-beauty.png", "Beauty"),
            Product("Mascara", "Volumizing", "₹300", "https://i.imgur.com/dummy-beauty2.png", "Beauty"),
            Product("Mouse", "Wireless", "₹800", "https://i.imgur.com/dummy-electronics.png", "Electronics")
        )
    }
}

