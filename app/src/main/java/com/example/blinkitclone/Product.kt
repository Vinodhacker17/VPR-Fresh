package com.example.blinkitclone

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val category: String, // e.g., "Dairy, Bread & Eggs"
    val subCategory: String // e.g., "Milk"
) : Parcelable
