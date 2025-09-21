package com.example.blinkitclone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrdersFragment : Fragment() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalAmountText: TextView
    private lateinit var checkoutButton: Button
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)
        cartRecyclerView = view.findViewById(R.id.cart_recycler_view)
        totalAmountText = view.findViewById(R.id.total_amount_text)
        checkoutButton = view.findViewById(R.id.checkout_button)

        // --- NEW --- Handle checkout button click
        checkoutButton.setOnClickListener {
            // Clear the cart
            Cart.clearCart()

            // Go to the order placed screen
            val intent = Intent(activity, OrderPlacedActivity::class.java)
            startActivity(intent)
            activity?.finish() // Close MainActivity so user starts fresh next time
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val cartItems = Cart.getItems()
        cartAdapter = CartAdapter(cartItems.toMutableList(), requireContext()) { product ->
            Cart.removeItem(product)
            refreshCartData()
        }
        cartRecyclerView.adapter = cartAdapter
        refreshCartData()
    }

    private fun refreshCartData() {
        val newCartItems = Cart.getItems()
        cartAdapter.updateList(newCartItems)
        totalAmountText.text = Cart.getTotalPrice()
    }
}