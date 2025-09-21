package com.example.blinkitclone

// Represents a whole section, like "Grocery & Kitchen", which contains a list of sub-categories
data class MainCategory(
    val title: String,
    val subCategories: List<SubCategory>
)
