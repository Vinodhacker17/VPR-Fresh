package com.example.blinkitclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button // Keep Button or change to MaterialButton if needed
import android.widget.LinearLayout
import android.widget.RelativeLayout // Import RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager // Import LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton // Import MaterialButton

class OrdersFragment : Fragment() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var grandTotalText: TextView
    private lateinit var checkoutButton: MaterialButton // Use MaterialButton type
    private lateinit var cartAdapter: CartAdapter
    private lateinit var populatedCartLayout: RelativeLayout // Use RelativeLayout type
    private lateinit var emptyCartLayout: LinearLayout
    private lateinit var startShoppingButton: MaterialButton // Use MaterialButton type


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        // Find views
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view)
        grandTotalText = view.findViewById(R.id.grand_total_text)
        checkoutButton = view.findViewById(R.id.checkout_button)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        populatedCartLayout = view.findViewById(R.id.populated_cart_layout)
        emptyCartLayout = view.findViewById(R.id.empty_cart_layout)
        startShoppingButton = view.findViewById(R.id.start_shopping_button)

        toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, HomeFragment())
                ?.commit()
        }

        checkoutButton.setOnClickListener {
            Cart.clearCart()
            (activity as? MainActivity)?.updateCartBadge()
            val intent = Intent(activity, OrderPlacedActivity::class.java)
            startActivity(intent)
            // Consider if you really want to finish MainActivity here
            // activity?.finish()
        }

        startShoppingButton.setOnClickListener {
            val bottomNav = activity?.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
            bottomNav?.selectedItemId = R.id.nav_home
        }

        // --- NEW --- Set LayoutManager here once
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshCart() // Refresh cart when the fragment becomes visible
    }

    private fun refreshCart() {
        val cartItems = Cart.getItemsWithQuantity()

        if (cartItems.isEmpty()) {
            emptyCartLayout.visibility = View.VISIBLE
            populatedCartLayout.visibility = View.GONE
        } else {
            emptyCartLayout.visibility = View.GONE
            populatedCartLayout.visibility = View.VISIBLE

            // Check if adapter needs initialization or just update
            // Also assign LayoutManager if RecyclerView is recreated (less likely in Fragments)
            if (cartRecyclerView.layoutManager == null) {
                cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

            if (!::cartAdapter.isInitialized || cartRecyclerView.adapter == null) {
                cartAdapter = CartAdapter(cartItems.toMutableMap(), requireContext()) {
                    refreshCart() // Refresh the cart list and total
                }
                cartRecyclerView.adapter = cartAdapter
            } else {
                cartAdapter.updateData(cartItems)
            }

            // Update the total price display
            val totalPrice = Cart.getTotalPrice()
            grandTotalText.text = "₹${"%.2f".format(totalPrice)}"
        }
        // Update the badge in MainActivity regardless of cart state
        (activity as? MainActivity)?.updateCartBadge()
    }
}