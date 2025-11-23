package com.example.blinkitclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ProductListActivity : AppCompatActivity() {

    private lateinit var sidebarRecyclerView: RecyclerView
    private lateinit var productGridRecyclerView: RecyclerView
    private lateinit var viewCartBar: MaterialButton
    private lateinit var productAdapter: ProductAdapter
    private var allProducts = createProductList() // Get the full product list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() } // Go back

        sidebarRecyclerView = findViewById(R.id.sidebar_recycler_view)
        productGridRecyclerView = findViewById(R.id.product_grid_recycler_view)
        viewCartBar = findViewById(R.id.view_cart_bar)

        val categoryName = intent.getStringExtra("categoryName") ?: "Unknown"
        toolbar.title = categoryName

        // 1. Filter products for the given main category
        val categoryProducts = allProducts.filter { it.category == categoryName }

        // 2. Find unique sub-categories for the sidebar
        val subcategories = categoryProducts.map { it.subCategory }.distinct()

        // 3. Setup sidebar
        val sidebarAdapter = SubCategoryFilterAdapter(subcategories) { selectedSubCategory ->
            // Filter grid when a sub-category is clicked
            val filteredProducts = categoryProducts.filter { it.subCategory == selectedSubCategory }
            productAdapter.updateList(filteredProducts)
        }
        sidebarRecyclerView.adapter = sidebarAdapter

        // 4. Setup product grid
        productAdapter = ProductAdapter(categoryProducts.toMutableList(), this) {
            updateCartBar() // Update cart bar when item is added
        }
        productGridRecyclerView.adapter = productAdapter

        // 5. Setup cart bar
        viewCartBar.setOnClickListener {
            // Open the cart screen (MainActivity, Cart tab)
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("navigateTo", "Cart")
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartBar() // Update cart bar every time this screen is shown
    }

    private fun updateCartBar() {
        val itemCount = Cart.getItemCount()
        if (itemCount > 0) {
            viewCartBar.visibility = View.VISIBLE
            viewCartBar.text = "$itemCount Items | ${Cart.getTotalPrice()}"
        } else {
            viewCartBar.visibility = View.GONE
        }
    }

    // This is temporary. Ideally, this list is loaded from a database or API
    // Updated product list with EXACT matching Category names
    private fun createProductList(): List<Product> {
        return listOf(
            // 1. Vegetables & Fruits
            Product("Fresh Tomato", "1 kg", "₹40", "https://i.postimg.cc/BbB9qdkG/OIP2.webp", "Vegetables & Fruits", "Daily Veggies"),
            Product("Red Onion", "1 kg", "₹30", "https://i.postimg.cc/kMYprpq2/onion-5-kg-pack-product-images-o590001744-p590001744-1-202410141659.webp", "Vegetables & Fruits", "Daily Veggies"),
            Product("Potato", "1 kg", "₹35", "https://i.postimg.cc/sXDwqpLB/potato-approx-900-g-1000-g-product-images-o590001952-p611163421-0-202510272019.jpg", "Vegetables & Fruits", "Daily Veggies"),

            // 2. Dairy & Breakfast
            Product("Amul Taaza Milk", "500 ml", "₹27", "https://i.postimg.cc/Dyb59fxx/amul-taaza-toned-milk-1-l-pouch-product-images-o590000597-p590049233-0-202510091903.webp", "Dairy & Breakfast", "Milk"),
            Product("Brown Bread", "1 packet", "₹45", "https://i.postimg.cc/TYMK7gT8/OIP1.webp", "Dairy & Breakfast", "Bread"),
            Product("Eggs", "6 pcs", "₹50", "https://i.postimg.cc/63mLPFny/chicken-eggs-55883-9296.avif", "Dairy & Breakfast", "Eggs"),

            // 3. Cold Drinks & Juices
            Product("Coca Cola", "750 ml", "₹40", "https://i.postimg.cc/sgr8KZ39/3e80a900-7ada-4747-8cef-6d3323926174.webp", "Cold Drinks & Juices", "Soft Drinks"),
            Product("Real Mixed Fruit", "1 L", "₹110", "https://i.postimg.cc/mk9XyTJL/32d2cb81-ba7e-4e32-aec4-2036352013ab.webp", "Cold Drinks & Juices", "Juices"),

            // 4. Snacks & Munchies
            Product("Lays Classic", "50g", "₹20", "https://i.postimg.cc/ydgv7VVF/tr-w-3840-c-at-max-cm-pad-resize-ar-1210-700-pr-true-f-auto-q-70-l-image-i-Wellness-logo-BDwqb-Qao9.webp", "Snacks & Munchies", "Chips"),
            Product("Haldiram Bhujia", "200g", "₹45", "https://i.postimg.cc/SxSdJLyw/bhujiya-sev-1.webp", "Snacks & Munchies", "Namkeen"),

            // 5. Grocery & Staples
            Product("Aashirvaad Atta", "5 kg", "₹240", "https://i.postimg.cc/DygdkWqP/product-f17cb7cc-9e2b-42a6-9cf5-ade60f9f4237.webp", "Grocery & Staples", "Atta"),
            Product("Daawat Rice", "1 kg", "₹150", "https://i.postimg.cc/wx5DLt4b/Daawat-Traditional-Basmati-Rice-5-Kg.jpg", "Grocery & Staples", "Rice"),
            Product("Tata Salt", "1 kg", "₹25", "https://i.postimg.cc/KzSggFPy/8df34e1b-ef3b-4c14-863a-5904612665f3202392595.webp", "Grocery & Staples", "Salt & Sugar"),




            // 7. Bakery & Biscuits
            Product("Parle-G", "100g", "₹10", "https://i.postimg.cc/9FFw9L7p/glucose-biscuit-799g-parle-g-baazwsh-174862.webp", "Bakery & Biscuits", "Biscuits"),
            Product("Oreo", "120g", "₹30", "https://i.postimg.cc/T1kwQXDp/65f042ff8e711d3ed73d9e90-oreo-chocolate-sandwich-cookies-family.jpg", "Bakery & Biscuits", "Biscuits"),

            // 8. Sweet Tooth
            Product("Dairy Milk Silk", "60g", "₹80", "https://i.postimg.cc/hjsPPqMH/Dairy-Milk-Silk-60-Gm-1.jpg", "Sweet Tooth", "Chocolates"),
            Product("Kwality Walls", "700ml", "₹200", "https://i.postimg.cc/yNzVmNGH/kwality-wall-s-shameless-vanilla-ice-cream-700-ml-carton-product-images-o491390853-p590110236-0-2022.webp", "Sweet Tooth", "Ice Cream"),



            // 10. Gourmet & World Food


            // 11. Chicken, Meat & Fish
            Product("Chicken Breast", "500g", "₹220", "https://i.postimg.cc/y80K8HNK/breastbonless.webp", "Chicken, Meat & Fish", "Chicken"),
            Product("Fresh Rohu Fish", "1kg", "₹300", "https://i.postimg.cc/dtyP5Yrq/51DYChbf2BL-AC-UF894-1000-QL80.jpg", "Chicken, Meat & Fish", "Fish"),

            // 12. Personal Care
            Product("Dove Soap", "100g", "₹40", "https://i.postimg.cc/5yQGKBHv/russia-moscow-february-21-2021-600nw-1921596098.webp", "Personal Care", "Bath & Body"),
            Product("Nivea Men Face Wash", "100ml", "₹150", "https://i.postimg.cc/MTgxGKVF/fc934a54005808919604-1n.avif", "Personal Care", "Skin Care"),

            // 13. Home & Cleaning
            Product("Surf Excel", "1 kg", "₹130", "https://i.postimg.cc/9fMjtqBt/surf-excel-quick-wash-detergent-powder-quick-pantry-3.webp", "Home & Cleaning", "Detergents"),
            Product("Harpic", "500ml", "₹90", "https://i.postimg.cc/vHsF2Kz9/harpic-disinfectant-toilet-cleaner-quick-pantry.webp", "Home & Cleaning", "Cleaners"),

            // 14. Pharma & Baby Care (Matches 'Pharma & Baby Care' or 'Pharma & Baby')
            // Note: I am adding both name variations here to be safe.
            Product("Pampers Diapers", "M Size", "₹600", "https://i.postimg.cc/3RvVnj0V/0e9ac2c4987176183989-1.avif", "Pharma & Baby", "Baby Care"),
            Product("Crocin", "15 tabs", "₹30", "https://i.postimg.cc/kG7sKWQw/crocin-650-paracetamol-tablets-953.jpg", "Pharma & Baby", "Medicines"),


            // 15. Stationery & Daily Needs


            // 16. Pet Care
            Product("Pedigree", "1.2 kg", "₹350", "https://i.postimg.cc/qBZ688XL/pedigree-mini-beef-lamb-and-vegetable-27kg-dog-dry-food.png", "Pet Care", "Dog Food"),
            Product("Whiskas", "1 kg", "₹300", "https://i.postimg.cc/pdZpy8dR/whiskasmilk.jpg", "Pet Care", "Cat Food")
        )
    }
    }


