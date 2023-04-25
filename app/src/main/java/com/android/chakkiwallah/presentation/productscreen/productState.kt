package com.android.chakkiwallah.presentation.productscreen

import com.android.chakkiwallah.domain.model.Product

data class productState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = ""
)