package com.example.blinkitclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class OrdersFragment : Fragment() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var grandTotalText: TextView
    private lateinit var checkoutButton: Button
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        // Find all the views from the layout
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view)
        grandTotalText = view.findViewById(R.id.grand_total_text)
        checkoutButton = view.findViewById(R.id.checkout_button)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            // Navigate back to HomeFragment
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, HomeFragment())
                ?.commit()
        }

        // --- THIS MAKES THE CHECKOUT BUTTON WORK ---
        checkoutButton.setOnClickListener {
            // Clear the cart
            Cart.clearCart()

            // Go to the order placed screen
            val intent = Intent(activity, OrderPlacedActivity::class.java)
            startActivity(intent)
        }
        // ---------------------------------------------

        return view
    }

    override fun onResume() {
        super.onResume()
        refreshCart()
    }

    private fun refreshCart() {
        val cartItems = Cart.getItemsWithQuantity()

        if (!::cartAdapter.isInitialized) {
            cartAdapter = CartAdapter(cartItems.toMutableMap(), requireContext()) {
                // This callback runs when quantity is changed in the adapter
                refreshCart()
            }
            cartRecyclerView.adapter = cartAdapter
        } else {
            cartAdapter.updateData(cartItems)
        }

        val totalPrice = Cart.getTotalPrice()
        grandTotalText.text = "₹${"%.2f".format(totalPrice)}"
    }
}