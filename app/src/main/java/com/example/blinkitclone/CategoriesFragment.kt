package com.example.blinkitclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        val mainCategoryRecyclerView = view.findViewById<RecyclerView>(R.id.main_category_recycler_view)

        mainCategoryRecyclerView.adapter = MainCategoryAdapter(createMainCategoryList())

        return view
    }

    private fun createMainCategoryList(): List<MainCategory> {
        // --- IMPORTANT: You must create a drawable resource for each sub-category image ---
        // If a drawable is missing, your app will crash.
        val grocerySubCategories = listOf(
            SubCategory("Vegetables & Fruits", R.drawable.ic_cat_veg_fruits),
            SubCategory("Atta, Rice & Dal", R.drawable.ic_cat_atta_rice),
            SubCategory("Oil, Ghee & Masala", R.drawable.ic_cat_oil_ghee),
            SubCategory("Dairy, Bread & Eggs", R.drawable.ic_cat_dairy_bread)
            // You can add more SubCategory items here
        )

        val snacksSubCategories = listOf(
            SubCategory("Chips & Namkeen", R.drawable.ic_cat_chips),
            SubCategory("Sweets & Chocolates", R.drawable.ic_cat_chocolates),
            SubCategory("Drinks & Juices", R.drawable.ic_cat_drinks),
            SubCategory("Tea, Coffee & Milks", R.drawable.ic_cat_tea_coffee)
            // You can add more SubCategory items here
        )

        // This is the final list that gets displayed
        return listOf(
            MainCategory("Grocery & Kitchen", grocerySubCategories),
            MainCategory("Snacks & Drinks", snacksSubCategories)
            // You can add more MainCategory sections here
        )
    }
}

