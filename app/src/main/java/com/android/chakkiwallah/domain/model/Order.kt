package com.android.chakkiwallah.domain.model

data class Order(
    val id: String,
    val userId: String,
    val products: List<Product>,
    val paymentId: String,
    val timestamp: Long,
    var isDelivered: Boolean = false // Default to false
)
