package com.android.chakkiwallah.presentation.homescreen

import com.android.chakkiwallah.domain.model.Product

data class HomeScreenState(
    val isLoading: Boolean = false,
    val product: List<Product>? = emptyList(),
    val error: String? = ""

)