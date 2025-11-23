package com.example.blinkitclone

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class CategoriesFragment : Fragment() {

    private lateinit var adapter: SubCategoryAdapter
    private lateinit var allCategories: List<SubCategory>

    // Location variables
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationTextView: TextView

    // Permission Launcher
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
        val view = inflater.inflate(R.layout.fragment_categories, container, false)

        // Find Views
        val recyclerView = view.findViewById<RecyclerView>(R.id.categories_recycler_view)
        val searchEditText = view.findViewById<EditText>(R.id.search_edit_text)
        locationTextView = view.findViewById(R.id.user_location) // Bind the location text view

        // Setup Location Logic
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationPermission()

        // Setup RecyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)

        // Load Data (Using online images)
        // Load Data (Using online images)
        allCategories = listOf(
            SubCategory("Vegetables & Fruits", "https://i.postimg.cc/Z5BLmv9z/Fruits-and-Vegetables.jpg"),
            SubCategory("Dairy & Breakfast", "https://i.postimg.cc/DfxQbPL7/20210510-063214-w19i-dairy-breakfast.png"),
            SubCategory("Cold Drinks & Juices", "https://i.postimg.cc/26zv115R/juice1.png"),
            SubCategory("Snacks & Munchies", "https://i.postimg.cc/GhZyk72L/img.webp"),
            SubCategory("Grocery & Staples", "https://i.postimg.cc/bJNDz461/branded-grocessary.png"),
            SubCategory("Bakery & Biscuits", "https://i.postimg.cc/Hn8Lnmbd/01-Tiffany-Biscuits-Composition-1608-X-1404-pixels.jpg"),
            SubCategory("Sweet Tooth", "https://i.postimg.cc/g0c8JwjZ/Popular-Sweets-Chocolate-Combo.jpg"),
            // Removed Instant Food
            SubCategory("Chicken, Meat & Fish", "https://i.postimg.cc/hjzJzVqk/set-meat-fish-steak-veal-steak-chicken-breast-top-view-black-stone-background-187166-8473.avif"),
            SubCategory("Personal Care", "https://i.postimg.cc/nVmjnK2d/set-of-different-cosmetics-group-many-various-beauty-products-bottles-other-packages-studio-shot-iso.jpg"),
            SubCategory("Home & Cleaning", "https://i.postimg.cc/VsX6xfL1/household-cleaning-products-formulation-consulting.jpg"),
            SubCategory("Pharma & Baby", "https://i.postimg.cc/Lsr9W7ch/Jain-medical-Baby-product.jpg"),
            SubCategory("Pet Care", "https://i.postimg.cc/XYP4Zm75/50-100-Lbs-Yellow-Green-Dog-Cat-Pet-Food-Packaging.avif")
        )

        adapter = SubCategoryAdapter(allCategories.toMutableList())
        recyclerView.adapter = adapter

        // Search Logic
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterCategories(s.toString())
            }
        })

        return view
    }

    private fun filterCategories(query: String) {
        val filteredList = allCategories.filter { category ->
            category.name.lowercase(Locale.getDefault()).contains(query.lowercase(Locale.getDefault()))
        }
        adapter.updateList(filteredList)
    }

    // --- LOCATION FUNCTIONS (Same as HomeFragment) ---
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
                    locationTextView.text = "Location Error"
                }
            }
        }
    }
}