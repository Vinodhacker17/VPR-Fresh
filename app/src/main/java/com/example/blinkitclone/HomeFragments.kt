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

        // All the old notification button logic has been removed from here.

        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        val categoryTabs = view.findViewById<TabLayout>(R.id.category_tabs)
        val profileIcon = view.findViewById<ImageView>(R.id.user_profile_icon)

        profileIcon.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
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
                val filteredList = if (selectedCategory == "All") allProducts else allProducts.filter { it.category == selectedCategory }
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
            Product("Cow Milk", "1 litre", "₹55", "https://i.postimg.cc/g0SfqNxy/milk-packet-png-9.png", "All"),
            Product("Brown Bread", "1 packet", "₹45", "https://i.postimg.cc/TYMK7gT8/OIP1.webp", "All"),
            Product("Farm Eggs", "6 pieces", "₹70", "https://i.postimg.cc/8zhk70vs/pngtree-farm-egg-png-image-14166321.png", "All"),
            Product("Ripe Tomatoes", "1 kg", "₹40", "https://i.postimg.cc/BbB9qdkG/OIP2.webp", "All"),
            Product("Fresh Carrots", "500 g", "₹30", "https://i.postimg.cc/C1b9N3Ff/pngtree-fresh-carrot-isolated-png-png-image-11516978.png", "All"),
            Product("Spinach Bunch", "250 g", "₹25", "https://i.postimg.cc/FRbnc0Zy/fresh-spinach-bunch-isolated-0m6g4yfufuqpjjml.png", "All"),
            Product("Aspirin", "10 tablets", "₹20", "https://i.postimg.cc/zG9dcx2w/OIP3.webp", "Pharmacy"),
            Product("Band-Aids", "20 strips", "₹50", "https://i.postimg.cc/fyT59pks/png-clipart-band-aid-adhesive-bandage-first-aid-kits-first-aid-supplies-others-textile-first-aid-sup.png", "Pharmacy"),
            Product("Cough Syrup", "100 ml", "₹120", "https://i.postimg.cc/y6QyGfkJ/cough.webp", "Pharmacy"),
            Product("Lipstick", "Matte Red", "₹450", "https://i.postimg.cc/C5vpd35h/pngtree-lipstick-and-gloss-makeup-cosmetics-products-png-image-12956252.png", "Beauty"),
            Product("Mascara", "Volumizing", "₹300", "https://i.postimg.cc/V61xgfbs/Mascara-PNG-Picture.png", "Beauty"),
            Product("Sunscreen", "SPF 50", "₹550", "https://i.postimg.cc/0NBXTVm8/d9j76-SA-Sunscreen-676x772plx.png", "Beauty"),
            Product("Wireless Mouse", "2.4GHz Optical", "₹800", "https://i.postimg.cc/bw6mnmmQ/61-Mk3-Yq-YHp-L.jpg", "Electronics"),
            Product("Earbuds", "Bluetooth 5.1", "₹1500", "https://i.postimg.cc/kMfRqLGF/1.png", "Electronics"),
            Product("Power Bank", "10000mAh", "₹1200", "https://i.postimg.cc/cJR6jbP3/hero-UM1107.webp", "Electronics"),
            Product("Scented Candle", "Lavender", "₹350", "https://i.postimg.cc/j255Dd26/R.png", "Decor"),
            Product("Photo Frame", "6x4 inch", "₹250", "https://i.postimg.cc/RZrSBT7L/OIP34.webp", "Decor"),
            Product("Toy Car", "Die-cast model", "₹400", "https://i.postimg.cc/3wrmXGLm/R-1.png", "Kids"),
            Product("Crayons", "12 pack", "₹80", "https://i.postimg.cc/TY762Mzx/Crayola-Crayon-Set-96-Assorted-Colors-ae8a3e4b-26da-4fc5-aaf1-f9de36c82b7c-6bc449cc47019355318add486.webp", "Kids"),
            Product("Gift Wrap", "2 meters", "₹100", "https://i.postimg.cc/WbKHfNRd/pngtree-christmas-gift-wrapping-png-image-10668882.png", "Gifting"),
            Product("Chocolate Box", "Assorted", "₹600", "https://i.postimg.cc/t4QzxYQN/pngtree-beautiful-chocolate-box-png-image-13340096.png", "Gifting"),
            Product("Imported Cheese", "Gouda, 200g", "₹750", "https://i.postimg.cc/FH3ttWcB/pngtree-cheese-dairy-product-png-image-11620721.png", "Premium"),
            Product("Dark Chocolate", "70% Cacao", "₹400", "https://i.postimg.cc/k4jzNk3B/Lindt-Excellence-85-Cocoa-Dark-Chocolate-2-X-100-G-768x958.jpg", "Premium")
        )
    }
}

