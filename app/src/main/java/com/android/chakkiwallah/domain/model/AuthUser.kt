package com.android.chakkiwallah.domain.model

data class AuthUser(
    val email: String = "",
    val password: String = "",
    var id: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val cartProducts: List<Cart> = listOf(),
    val activeOrders: List<Product> = listOf(),)
