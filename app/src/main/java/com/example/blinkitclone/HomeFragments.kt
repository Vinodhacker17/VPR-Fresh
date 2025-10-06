package com.example.blinkitclone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private val allProducts = createProductList()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView

    private lateinit var bannerViewPager: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var autoSlideRunnable: Runnable

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getCurrentLocation()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initBanner(view)

        locationTextView = view.findViewById(R.id.user_location)
        val productRecyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        val categoryTabs = view.findViewById<TabLayout>(R.id.category_tabs)
        val profileIcon = view.findViewById<ImageView>(R.id.user_profile_icon)
        val searchEditText = view.findViewById<TextInputEditText>(R.id.search_edit_text)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationPermission()

        profileIcon.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }

        productAdapter = ProductAdapter(allProducts.toMutableList(), requireContext())
        productRecyclerView.adapter = productAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterProducts(s.toString())
            }
        })

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

    private fun initBanner(view: View) {
        bannerViewPager = view.findViewById(R.id.banner_view_pager)

        // --- THE ONLY CHANGE IS HERE ---
        // We now provide a list of online URLs instead of local files.
        val bannerImageUrls = listOf(
            "https://i.postimg.cc/cChXYtj9/super-sale-promotion-social-media-3d-render-template-design-banner-template-502896-377.jpg",
            "https://i.postimg.cc/1tqpsVs3/horizontal-sale-banner-template-for-vegetarianor.jpg",
            "https://i.postimg.cc/C1kfWysx/sale-and-offer-banner.jpg"
        )
        // -----------------------------

        val bannerAdapter = BannerAdapter(bannerImageUrls)
        bannerViewPager.adapter = bannerAdapter

        autoSlideRunnable = Runnable {
            val currentItem = bannerViewPager.currentItem
            val nextItem = if (currentItem == bannerAdapter.itemCount - 1) 0 else currentItem + 1
            bannerViewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(autoSlideRunnable, 3000)
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(autoSlideRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(autoSlideRunnable)
    }

    private fun filterProducts(query: String) {
        val filteredList = allProducts.filter { product ->
            product.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        }
        productAdapter.updateList(filteredList)
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }
            else -> {
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val locationString = "${address.locality ?: ""}, ${address.subLocality ?: ""}"
                        locationTextView.text = locationString
                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            }
        }
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