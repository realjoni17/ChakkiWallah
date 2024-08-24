package com.android.chakkiwallah.presentation.cart


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.chakkiwallah.common.Resource
import com.android.chakkiwallah.domain.model.Product
import com.android.chakkiwallah.presentation.animation.LoadingScreen
import com.android.chakkiwallah.presentation.login.LoginViewModel
import com.android.chakkiwallah.presentation.payment.PaymentViewModel

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    paymentViewModel: PaymentViewModel = hiltViewModel(),
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val cartItemsResource by cartViewModel.cartItems.collectAsState()
    val uid = loginViewModel.uid

    // Calculate the total price based on the current cart items
    val totalPrice : Double = when (cartItemsResource) {
        is Resource.Success -> {
            cartItemsResource.data?.sumOf { it.price * it.quantity } ?: 0.0
        }
        else -> 0.0
    }

    LaunchedEffect(key1 = true) {
        // Fetch cart items when the screen is launched
        if (uid != null) {
            cartViewModel.fetchCartItems(uid)
        } // Replace with actual user ID
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (cartItemsResource) {
            is Resource.Loading -> {
                // Show loading indicator
                LoadingScreen()
            }
            is Resource.Success -> {
                // Update UI with cart items
                val cartItems = cartItemsResource.data ?: emptyList()

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        CartItemCard(item)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total: ₹${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.h6
                )

                Button(
                    onClick ={
                        paymentViewModel.initiatePayment(navController.context, Product(name = "Total Payment", price = totalPrice), totalPrice.toInt())

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = -128.dp)
                ) {
                    Text("Checkout")
                }
            }
            is Resource.Error -> {
                // Show error message
                Text(
                    text = "Error: ${cartItemsResource.message}",
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}

@Composable
fun CartItemCard(item: Product) {
    // Display cart item details like name, price, quantity, etc.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = item.name, style = MaterialTheme.typography.h6)
            Text(text = "Price: ₹${"%.2f".format(item.price)}")
            Text(text = "Quantity: ${item.quantity}")
            // You can add buttons to increase/decrease quantity here
        }
    }
}
